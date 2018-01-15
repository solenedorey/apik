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
        StringBuilder result = new StringBuilder();
        for (TrackPoint trackPoint : trackPoints) {
            result.append(trackPoint.toString());
        }
        return result.toString();
    }

    public ArrayList<TrackPoint> getMinAndMaxLocalPeaks() {
        ArrayList<TrackPoint> minAndMaxPeaks = new ArrayList<>();
        minAndMaxPeaks.add(trackPoints.get(0));
        for (int i = 1; i < trackPoints.size() - 1; i++) {
            if ((trackPoints.get(i - 1).getElevation() < trackPoints.get(i).getElevation() && trackPoints.get(i).getElevation() > trackPoints.get(i + 1).getElevation()) || (trackPoints.get(i - 1).getElevation() > trackPoints.get(i).getElevation() && trackPoints.get(i).getElevation() < trackPoints.get(i + 1).getElevation())) {
                minAndMaxPeaks.add(trackPoints.get(i));
            }
        }
        minAndMaxPeaks.add(trackPoints.get(trackPoints.size()-1));
        return minAndMaxPeaks;
    }
}
