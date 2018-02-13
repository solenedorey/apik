package projetAnnuel;

import projetAnnuel.events.ModelListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TrackChart extends JPanel implements ModelListener {

    private Track track;
    private double metersToPixelsX;
    private double metersToPixelsY;
    public static int WIDTH = 800;
    public static int HEIGHT = 400;

    public TrackChart() {
        this.track = null;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        if (track != null) {
            WIDTH = getWidth();
            HEIGHT = getHeight();
            metersToPixelsX = WIDTH / track.getTotalDistance();
            metersToPixelsY = HEIGHT / track.getMaxElevation();
            /*double amplitude = track.getMaxElevation() - track.getMinElevation();
            metersToPixelsY = HEIGHT / amplitude;*/

            ArrayList<TrackPoint> trackPoints = track.getTrackPoints();
            ArrayList<TrackSection> trackSections = track.getTrackSections();

            Color color;
            if (trackPoints.get(0).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMaximum) {
                color = Color.blue;
            } else {
                color = Color.red;
            }

            /*Color color;
            ArrayList<TrackSection> trackSections = track.getTrackSections();
            if (trackSections.get(0).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMaximum) {
                color = Color.blue;
            } else {
                color = Color.red;
            }*/

            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            for (int i = 0; i < trackPoints.size() - 1; i++) {
                graphics2D.setColor(color);
                if (i != 0) {
                    x1 = x2;
                    y1 = y2;
                } else {
                    y1 = HEIGHT - (trackPoints.get(i).getElevation() * metersToPixelsY);
                }
                x2 = x1 + trackPoints.get(i).getDistanceToNextPoint() * metersToPixelsX;
                y2 = HEIGHT - (trackPoints.get(i + 1).getElevation() * metersToPixelsY);
                graphics2D.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                /*if (trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMaximum || trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.Downhill) {
                    color = Color.blue;
                } else if (trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMinimum || trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.Uphill) {
                    color = Color.red;
                }*/
                /*if (trackSections.indexOf(trackPoints.get(i)) != -1) {
                    color = (color == Color.blue ? Color.red : Color.blue);
                }*/
                for (TrackSection trackSection : trackSections) {
                    if (trackSection.getStartIndex() == trackPoints.get(i)) {
                        color = (color == Color.blue ? Color.red : Color.blue);
                    }
                }
            }
            for (TrackSection trackSection : trackSections) {
                System.out.println("km/h : " + trackSection.getAverageSpeedInKmPerHour());
                System.out.println("m/s : " + trackSection.getAverageSpeedInMetersPerSecond());
                System.out.println("----");
            }
        } else {
            this.add(new JLabel("Aucun track n'a été chargé."));
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
        repaint();
    }
}
