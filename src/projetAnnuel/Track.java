package projetAnnuel;

import java.util.ArrayList;

public class Track {

    // Liste de TrackPoints
    private ArrayList<TrackPoint> trackPoints;

    /**
     * Constructor.
     */
    public Track() {
        this.trackPoints = new ArrayList<>();
    }

    /**
     * Permet l'ajout d'un TrackPoint à la liste
     *
     * @param trackPoint (required) une instance de la classe TrackPoint
     */
    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoints.add(trackPoint);
    }

    @Override
    public String toString() {
        String result = "";
        for (TrackPoint trackPoint : trackPoints) {
            result += trackPoint.toString();
        }
        return result;
    }
}
