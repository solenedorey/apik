package projetAnnuel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TrackChart extends JPanel {

    private Track track;
    private double metersToPixelsX;
    private double metersToPixelsY;
    public static int WIDTH = 1800;
    public static int HEIGHT = 900;

    public TrackChart(Track track) {
        this.track = track;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        metersToPixelsX = WIDTH / track.getTotalDistance();
        metersToPixelsY = HEIGHT / track.getMaxElevation();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        Color color;
        ArrayList<TrackPoint> trackPoints = track.getTrackPoints();
        if (trackPoints.get(0).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMaximum) {
            color = Color.blue;
        } else {
            color = Color.red;
        }
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
                y1 = HEIGHT - trackPoints.get(i).getElevation() * metersToPixelsY;
            }
            x2 = x1 + trackPoints.get(i).getDistanceToNextPoint() * metersToPixelsX;
            y2 = HEIGHT - trackPoints.get(i + 1).getElevation() * metersToPixelsY;
            graphics2D.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            if (trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMaximum || trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.Downhill) {
                color = Color.blue;
            } else if (trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.LocalMinimum || trackPoints.get(i).getTrackPointCategory() == TrackPoint.TrackPointCategory.Uphill) {
                color = Color.red;
            }
        }
    }

}
