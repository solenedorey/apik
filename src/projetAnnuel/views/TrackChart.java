package projetAnnuel.views;

import projetAnnuel.models.ChartPoint;
import projetAnnuel.models.Track;
import projetAnnuel.models.TrackPoint;
import projetAnnuel.models.TrackSection;
import projetAnnuel.events.ModelListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class TrackChart extends JPanel implements ModelListener, MouseMotionListener {

    private Track track;

    private JLabel noTrackLabel;

    private double metersToPixelsX;
    private double metersToPixelsY;
    public static int WIDTH = 800;
    public static int HEIGHT = 400;
    public static int PADDING = 60;

    private ArrayList<ChartPoint> chartPoints;

    private Ellipse2D ellipse2D;
    private String elevationLabel;

    public TrackChart() {
        track = null;
        chartPoints = new ArrayList<>();
        noTrackLabel = null;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
            WIDTH = getWidth();
            HEIGHT = getHeight();
            metersToPixelsX = (WIDTH - PADDING * 2) / track.getTotalDistance();
            /*metersToPixelsY = HEIGHT / track.getMaxElevation();*/
            double amplitude = track.getMaxElevation() - track.getMinElevation();
            metersToPixelsY = (HEIGHT - PADDING * 2) / amplitude;

            AffineTransform at = new AffineTransform();
            at.setToRotation(Math.toRadians(-90), PADDING, PADDING);
            graphics2D.setTransform(at);

            graphics2D.drawString("Altitude en m", 0, PADDING /2);

            at.setToRotation(Math.toRadians(0));
            graphics2D.setTransform(at);

            graphics2D.drawString("Distance en m", WIDTH - PADDING * 2, HEIGHT - PADDING / 2);

            graphics2D.drawLine(PADDING, PADDING, PADDING, HEIGHT - PADDING);
            graphics2D.drawLine(PADDING, HEIGHT - PADDING, WIDTH - PADDING, HEIGHT - PADDING);

            ArrayList<TrackPoint> trackPoints = track.getTrackPoints();
            ArrayList<TrackSection> trackSections = track.getTrackSections();

            Color color;
            if (trackPoints.get(0).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMaximum) {
                color = Color.blue;
            } else {
                color = Color.red;
            }

            double x1 = PADDING;
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
                    y1 = HEIGHT - PADDING - (trackPoints.get(i).getElevation() - track.getMinElevation()) * metersToPixelsY;
                }
                x2 = x1 + trackPoints.get(i).getDistanceToNextPoint() * metersToPixelsX;
                y2 = HEIGHT - PADDING - (trackPoints.get(i + 1).getElevation() - track.getMinElevation()) * metersToPixelsY;
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
                Boolean isInRightSide = false;
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
