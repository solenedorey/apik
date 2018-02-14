package projetAnnuel;

import projetAnnuel.events.ModelListener;

import javax.swing.*;
import java.awt.*;

public class StatsView extends JPanel {

    private Track track;
    private JScrollPane jScrollPane;

    public static int WIDTH = 400;

    public StatsView() {
        track = null;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, TrackChart.HEIGHT));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (track != null) {
            JTable jTable = new JTable(new TrackToTableModel(track));
            jScrollPane = new JScrollPane(jTable);
            this.add(jScrollPane, BorderLayout.CENTER);
        }
    }

    public void setTrack(Track track) {
        this.track = track;
        repaint();
    }
}
