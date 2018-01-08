package projetAnnuel;

import java.util.ArrayList;

public class TrackSegment {

    // Liste de TrackPoints
    private ArrayList<TrackPoint> trackPoints;

    /**
     * Constructor.
     */
    public TrackSegment() {
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
}
