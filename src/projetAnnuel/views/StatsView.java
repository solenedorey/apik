package projetAnnuel.views;

import projetAnnuel.models.Track;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class StatsView extends JPanel {

    private Track track;
    private JScrollPane jScrollPane;
    private Boolean areAboutSkiDescents;
    private JLabel noTrackLabel;

    public static int WIDTH = 400;

    public StatsView(Boolean areAboutSkiDescents) {
        this.areAboutSkiDescents = areAboutSkiDescents;
        track = null;
        noTrackLabel = null;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, TrackChart.HEIGHT / 2));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (track != null) {
            this.remove(noTrackLabel);
            JTable jTable = new JTable(new TrackToTableModel(track, areAboutSkiDescents));
            MultiLineTableHeaderRenderer multiLineTableHeaderRenderer = new MultiLineTableHeaderRenderer();
            jTable.getTableHeader().setDefaultRenderer(multiLineTableHeaderRenderer);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            jTable.setDefaultRenderer(Object.class, centerRenderer);
            jScrollPane = new JScrollPane(jTable);
            this.add(jScrollPane, BorderLayout.CENTER);
        } else {
            if (noTrackLabel == null) {
                noTrackLabel = new JLabel("Aucun track n'a été chargé.");
                noTrackLabel.setHorizontalAlignment(JLabel.CENTER);
                this.add(noTrackLabel, BorderLayout.CENTER);
            }
        }
    }

    public void setTrack(Track track) {
        this.track = track;
        repaint();
    }
}
