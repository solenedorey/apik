package projetAnnuel.views;

import projetAnnuel.events.ModelListener;
import projetAnnuel.models.Track;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Vue sur les statistiques des montées et des descentes d'un track
 */
public class StatsView extends JPanel implements ModelListener {

    private Track track;

    private TrackToTableModel trackToDescentsTableModel;
    private TrackToTableModel trackToAscentsTableModel;

    public static int WIDTH = 400;

    public StatsView() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Statistiques",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        setLayout(new GridLayout(2, 1));

        track = null;

        JPanel descentsPanel = new JPanel();
        descentsPanel.setLayout(new BorderLayout());
        descentsPanel.setPreferredSize(new Dimension(WIDTH, TrackChart.HEIGHT / 2));

        JLabel descentsLabel = new JLabel("Descentes");
        descentsLabel.setForeground(Color.BLUE);
        descentsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        descentsPanel.add(descentsLabel, BorderLayout.NORTH);

        trackToDescentsTableModel = new TrackToTableModel(track, true);
        JScrollPane descentsTable = buildTable(trackToDescentsTableModel);
        descentsTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLUE, Color.BLUE));
        descentsPanel.add(descentsTable, BorderLayout.CENTER);

        JPanel ascentsPanel = new JPanel();
        ascentsPanel.setLayout(new BorderLayout());
        ascentsPanel.setPreferredSize(new Dimension(WIDTH, TrackChart.HEIGHT / 2));

        JLabel ascentsLabel = new JLabel("Montées");
        ascentsLabel.setForeground(Color.RED);
        ascentsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ascentsPanel.add(ascentsLabel, BorderLayout.NORTH);

        trackToAscentsTableModel = new TrackToTableModel(track, false);
        JScrollPane ascentsTable = buildTable(trackToAscentsTableModel);
        ascentsTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, Color.RED));
        ascentsPanel.add(ascentsTable, BorderLayout.CENTER);

        add(descentsPanel);
        add(ascentsPanel);
    }

    public JScrollPane buildTable(TrackToTableModel trackToTableModel) {
        JTable jTable = new JTable(trackToTableModel);
        MultiLineTableHeaderRenderer multiLineTableHeaderRenderer = new MultiLineTableHeaderRenderer();
        jTable.getTableHeader().setDefaultRenderer(multiLineTableHeaderRenderer);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, centerRenderer);
        return new JScrollPane(jTable);
    }

    public void setTrack(Track track) {
        if (this.track != null) {
            track.removeListener(this);
        }
        this.track = track;
        track.addListener(this);
        trackToAscentsTableModel.setTrack(track);
        trackToDescentsTableModel.setTrack(track);
        repaint();
    }

    @Override
    public void updateModel(Object object) {
        repaint();
    }
}
