package projetAnnuel.views;

import projetAnnuel.models.*;
import projetAnnuel.events.ModelListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * Vue sur le track permettant de le visualiser en 2D
 */
public class TrackChart extends JPanel implements ModelListener, MouseMotionListener {

    private Track track;

    private JLabel noTrackLabel;

    private int width;
    private int height;
    private int padding;

    private ArrayList<ChartPoint> chartPoints;

    private Ellipse2D ellipse2D;
    private String elevationLabel;

    public TrackChart() {
        width = 800;
        height = 400;
        padding = 60;
        track = null;
        chartPoints = new ArrayList<>();
        noTrackLabel = null;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Visualisation du tracé GPS",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        if (track != null) {
            this.remove(noTrackLabel);
            width = getWidth();
            height = getHeight();
            double metersToPixelsX = (width - padding * 2) / track.getTotalDistance();
            /*metersToPixelsY = height / track.getMaxElevation();*/
            double amplitude = track.getMaxElevation() - track.getMinElevation();
            double metersToPixelsY = (height - padding * 2) / amplitude;

            AffineTransform at = new AffineTransform();
            at.setToRotation(Math.toRadians(-90), padding, padding);
            graphics2D.setTransform(at);

            graphics2D.drawString("Altitude en m", 0, padding /2);

            at.setToRotation(Math.toRadians(0));
            graphics2D.setTransform(at);

            graphics2D.drawString("Distance en m", width - padding * 2, height - padding / 2);

            graphics2D.drawLine(padding, padding, padding, height - padding);
            graphics2D.drawLine(padding, height - padding, width - padding, height - padding);

            ArrayList<TrackPoint> trackPoints = track.getTrackPoints();
            ArrayList<TrackSection> trackSections = track.getTrackSections();

            Color color;
            if (trackPoints.get(0).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMaximum) {
                color = Color.blue;
            } else {
                color = Color.red;
            }

            double x1 = padding;
            double y1;
            double x2 = 0;
            double y2 = 0;


            chartPoints = new ArrayList<>();

            for (int i = 0; i < trackPoints.size() - 1; i++) {
                graphics2D.setColor(color);
                if (i != 0) {
                    x1 = x2;
                    y1 = y2;
                } else {
                    y1 = height - padding - (trackPoints.get(i).getElevation() - track.getMinElevation()) * metersToPixelsY;
                }
                x2 = x1 + trackPoints.get(i).getDistanceToNextPoint() * metersToPixelsX;
                y2 = height - padding - (trackPoints.get(i + 1).getElevation() - track.getMinElevation()) * metersToPixelsY;
                graphics2D.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                if (i == 0) {
                    chartPoints.add(new ChartPoint((int) x1, (int) y1, String.valueOf((int) trackPoints.get(i).getElevation())));
                }
                chartPoints.add(new ChartPoint((int) x2, (int) y2, String.valueOf((int) trackPoints.get(i + 1).getElevation())));

                for (TrackSection trackSection : trackSections) {
                    if (trackSection.getStartIndex() == trackPoints.get(i)) {
                        color = (color == Color.blue ? Color.red : Color.blue);
                    }
                }
            }
            if (ellipse2D != null && elevationLabel != null) {
                boolean isInRightSide = false;
                if (ellipse2D.getX() > getWidth() / 2) {
                    isInRightSide = true;
                }
                graphics2D.setColor(Color.DARK_GRAY);
                graphics2D.fill(ellipse2D);

                FontMetrics fm = graphics2D.getFontMetrics();
                Rectangle2D rect = fm.getStringBounds(elevationLabel, graphics2D);


                graphics2D.setColor(Color.WHITE);
                int rectX;
                int labelX;
                if (isInRightSide) {
                    rectX = (int) ellipse2D.getX() - 7 - fm.stringWidth(elevationLabel);
                    labelX = (int) ellipse2D.getX() - 5 - fm.stringWidth(elevationLabel);
                } else {
                    rectX = (int) ellipse2D.getX() + 13;
                    labelX = (int) ellipse2D.getX() + 15;
                }
                graphics2D.fillRect(
                        rectX,
                        (int) ellipse2D.getY() + 14 - fm.getAscent(),
                        (int) rect.getWidth() + 4,
                        (int) rect.getHeight() + 4);

                graphics2D.setColor(Color.DARK_GRAY);
                graphics2D.drawString(elevationLabel, labelX, (int) ellipse2D.getY() + 15);
            }
        } else {
            if (noTrackLabel == null) {
                noTrackLabel = new JLabel("Aucun track n'a été chargé.");
                noTrackLabel.setHorizontalAlignment(JLabel.CENTER);
                this.add(noTrackLabel, BorderLayout.CENTER);
            }
        }
    }

    @Override
    public void updateModel(Object o) {
        repaint();
    }

    public void setTrack(Track track) {
        if (this.track != null) {
            track.removeListener(this);
        }
        this.track = track;
        track.addListener(this);
        addMouseMotionListener(this);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        for (ChartPoint chartPoint : chartPoints) {
            if (chartPoint.getX() == mouseX) {
                ellipse2D = new Ellipse2D.Double(chartPoint.getX() - 5,chartPoint.getY() - 5,10,10);
                elevationLabel = chartPoint.getLabel();
                repaint();
            }
        }
    }
}
