package projetAnnuel;

import java.util.ArrayList;

public class Track {

    // Liste de TrackSegments
    private ArrayList<TrackSegment> trackSegments;
    private String name;

    /**
     * Constructor.
     */
    public Track(String name) {
        this.name = name;
        this.trackSegments = new ArrayList<>();
    }

    /**
     * Permet l'ajout d'un TrackSegment à la liste
     *
     * @param trackSegment (required) une instance de la classe TrackSegment
     */
    public void addTrackPoint(TrackSegment trackSegment) {
        trackSegments.add(trackSegment);
    }

    public String getName() {
        return name;
    }
}
