package projetAnnuel.views;

import projetAnnuel.events.ModelListener;
import projetAnnuel.models.Track;
import projetAnnuel.models.TrackSection;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;

public class TrackToTableModel extends AbstractTableModel implements ModelListener {

    private Track track;

    private final static int DISTANCE = 0;
    private final static int TIME = 1;
    private final static int SPEED_M_S = 2;
    private final static int SPEED_KM_H = 3;
    private Boolean areAboutSkiDescents;

    private String[] columnName = {"Distance parcourue (m)", "Temps écoulé (min)", "Vitesse moyenne (m/s)", "Vitesse moyenne (km/h)"};

    public TrackToTableModel(Track track, Boolean areAboutSkiDescents) {
        this.track = track;
        this.track.addListener(this);
        this.areAboutSkiDescents = areAboutSkiDescents;
    }

    @Override
    public int getRowCount() {
        if (areAboutSkiDescents) {
            return track.getTrackDescendingSections().size();
        } else {
            return track.getTrackRisingSections().size();
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TrackSection trackSection;
        if (areAboutSkiDescents) {
            trackSection = track.getTrackDescendingSections().get(rowIndex);
        } else {
            trackSection  = track.getTrackRisingSections().get(rowIndex);
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        switch (columnIndex) {
            case DISTANCE:
                return decimalFormat.format(trackSection.getDistanceTravelled());
            case TIME:
                return decimalFormat.format(trackSection.getSpentTime() / 1000d / 60d);
            case SPEED_M_S:
                return decimalFormat.format(trackSection.getAverageSpeedInMetersPerSecond());
            case SPEED_KM_H:
                return decimalFormat.format(trackSection.getAverageSpeedInKmPerHour());
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
