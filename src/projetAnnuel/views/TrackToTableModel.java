package projetAnnuel.views;

import projetAnnuel.events.ModelListener;
import projetAnnuel.models.Track;
import projetAnnuel.models.TrackSection;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Adapteur Track vers Tableau
 */
public class TrackToTableModel extends AbstractTableModel implements ModelListener {

    private Track track;

    private final static int DISTANCE = 0;
    private final static int TIME = 1;
    private final static int SPEED_M_S = 2;
    private final static int SPEED_KM_H = 3;
    private Boolean areAboutSkiDescents;

    private String[] columnName = {"Distance parcourue (m)", "Temps écoulé (min:sec)", "Vitesse moyenne (m/s)", "Vitesse moyenne (km/h)"};

    public TrackToTableModel(Track track, Boolean areAboutSkiDescents) {
        this.track = track;
        this.areAboutSkiDescents = areAboutSkiDescents;
    }

    @Override
    public int getRowCount() {
        if (track !=  null) {
            if (areAboutSkiDescents) {
                return track.getDownhillSections().size();
            } else {
                return track.getUphillSections().size();
            }
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (track != null) {
            TrackSection trackSection;
            if (areAboutSkiDescents) {
                trackSection = track.getDownhillSections().get(rowIndex);
            } else {
                trackSection  = track.getUphillSections().get(rowIndex);
            }
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            switch (columnIndex) {
                case DISTANCE:
                    return decimalFormat.format(trackSection.getDistanceTravelled());
                case TIME:
                    DateFormat dateFormat = new SimpleDateFormat("mm:ss");
                    Date duration = new Date(trackSection.getSpentTime());
                    return dateFormat.format(duration);
                case SPEED_M_S:
                    return decimalFormat.format(trackSection.getAverageSpeedInMetersPerSecond());
                case SPEED_KM_H:
                    return decimalFormat.format(trackSection.getAverageSpeedInKmPerHour());
            }
            return null;
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public void updateModel(Object o) {
        fireTableDataChanged();
    }

    public void setTrack(Track track) {
        if (this.track != null) {
            track.removeListener(this);
        }
        this.track = track;
        track.addListener(this);
    }
}
