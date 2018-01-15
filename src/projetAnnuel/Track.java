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

    public void acquaintDistanceBetweenTrackpoints(){
        for (int i = 0; i < trackPoints.size() - 1 ; i++) {
            double lat_a = trackPoints.get(i).getLatitude();
            double lat_b = trackPoints.get(i+1).getLatitude();
            double lng_a = trackPoints.get(i).getLongitude();
            double lng_b = trackPoints.get(i+1).getLongitude();
            trackPoints.get(i).setDistanceToNextPoint(meterDistanceBetweenPoints(lat_a, lng_a, lat_b, lng_b));
        }
    }

    private double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }
}
