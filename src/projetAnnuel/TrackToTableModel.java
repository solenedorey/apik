package projetAnnuel;

import projetAnnuel.events.ModelListener;

import javax.swing.table.AbstractTableModel;

public class TrackToTableModel extends AbstractTableModel implements ModelListener {

    private Track track;

    private final static int DISTANCE = 0;
    private final static int TIME = 1;
    private final static int SPEED_M_S = 2;
    private final static int SPEED_KM_H = 3;

    private String[] columnName = {"Distance parcourue", "Temps écoulé", "Vitesse moyenne (m/s)", "Vitesse moyenne (km/h)"};

    public TrackToTableModel(Track track) {
        this.track = track;
        this.track.addListener(this);
    }

    @Override
    public int getRowCount() {
        return track.getTrackSections().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TrackSection trackSection = track.getTrackSections().get(rowIndex);
        switch (columnIndex) {
            case DISTANCE:
                return trackSection.getDistanceTravelled();
            case TIME:
                return trackSection.getSpentTime();
            case SPEED_M_S:
                return trackSection.getAverageSpeedInMetersPerSecond();
            case SPEED_KM_H:
                return trackSection.getAverageSpeedInKmPerHour();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public void updateModel(Object o) {
        fireTableDataChanged();
    }
}
