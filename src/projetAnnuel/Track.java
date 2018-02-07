package projetAnnuel;

import java.util.ArrayList;

public class Track {

    // Liste de TrackPoints
    private ArrayList<TrackPoint> trackPoints;
    // L'altitude maximum
    private double maxElevation;
    // L'altitude minimum
    private double minElevation;
    // La distance totale parcourue
    private double totalDistance;

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

    /**
     * Retourne la liste des TrackPoints
     *
     * @return une ArrayList : l'ensemble des TrackPoints qui constituent le track
     */
    public ArrayList<TrackPoint> getTrackPoints() {
        return trackPoints;
    }

    /**
     * Retourne l'altitude maximum du track
     *
     * @return un double
     */
    public double getMaxElevation() {
        return maxElevation;
    }

    /**
     * Retourne l'altitude minimum du track
     *
     * @return un double
     */
    public double getMinElevation() {
        return minElevation;
    }

    /**
     * Retourne la distance totale parcourue du Track
     *
     * @return un entier
     */
    public double getTotalDistance() {
        return totalDistance;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (TrackPoint trackPoint : trackPoints) {
            result.append(trackPoint.toString());
        }
        return result.toString();
    }


    /**
     * Complète les informations de chaque point du Track
     */
    public void prepare() {
        acquaintTrackPointsCategory();
        acquaintDistanceBetweenTrackpoints();
        computeTotalDistance();
        determineMaxAndMinElevation();

    }

    /**
     * Renseigne la distance totale du track
     */
    public void computeTotalDistance() {
        double distancesSum = 0;
        for (int i = 0; i < trackPoints.size() - 1; i++) {
            distancesSum += trackPoints.get(i).getDistanceToNextPoint();
        }
        this.totalDistance = distancesSum;
    }

    public void determineMaxAndMinElevation() {
        double maxElevation = 0;
        double minElevation = trackPoints.get(0).getElevation();
        for (TrackPoint trackPoint : trackPoints) {
            if (trackPoint.getElevation() > maxElevation) {
                maxElevation = trackPoint.getElevation();
            }
            if (trackPoint.getElevation() < minElevation) {
                minElevation = trackPoint.getElevation();
            }
        }
        this.maxElevation = maxElevation;
        this.minElevation = minElevation;
    }

    /**
     * Retourne la liste des minimums et maximums locaux
     * @return une ArrayList de TrackPoints
     */
    public ArrayList<TrackPoint> getMinAndMaxLocalPeaks() {
        ArrayList<TrackPoint> minAndMaxPeaks = new ArrayList<>();
        minAndMaxPeaks.add(trackPoints.get(0));
        for (int i = 1; i < trackPoints.size() - 1; i++) {
            if ((trackPoints.get(i - 1).getElevation() < trackPoints.get(i).getElevation() && trackPoints.get(i).getElevation() > trackPoints.get(i + 1).getElevation()) || (trackPoints.get(i - 1).getElevation() > trackPoints.get(i).getElevation() && trackPoints.get(i).getElevation() < trackPoints.get(i + 1).getElevation())) {
                minAndMaxPeaks.add(trackPoints.get(i));
            }
        }
        minAndMaxPeaks.add(trackPoints.get(trackPoints.size() - 1));
        return minAndMaxPeaks;
    }

    /**
     * Permet de renseigner pour chaque point s'il est de type "minimum local", "maximum local", "descente" ou "montée"
     */
    private void acquaintTrackPointsCategory() {
        if (trackPoints.get(0).getElevation() <= trackPoints.get(1).getElevation()) {
            trackPoints.get(0).setTrackPointCategory(TrackPoint.TrackPointCategory.LocalMinimum);
        } else {
            trackPoints.get(0).setTrackPointCategory(TrackPoint.TrackPointCategory.LocalMaximum);
        }
        for (int i = 1; i < trackPoints.size() - 1; i++) {
            double previousElevation = trackPoints.get(i - 1).getElevation();
            double currentElevation = trackPoints.get(i).getElevation();
            double nextElevation = trackPoints.get(i + 1).getElevation();
            if (previousElevation < currentElevation) {
                if (currentElevation < nextElevation || currentElevation == nextElevation) {
                    trackPoints.get(i).setTrackPointCategory(TrackPoint.TrackPointCategory.Uphill);
                } else if (currentElevation > nextElevation) {
                    trackPoints.get(i).setTrackPointCategory(TrackPoint.TrackPointCategory.LocalMaximum);
                }
            } else if (previousElevation > currentElevation) {
                if (currentElevation > nextElevation || currentElevation == nextElevation) {
                    trackPoints.get(i).setTrackPointCategory(TrackPoint.TrackPointCategory.Downhill);
                } else if (currentElevation < nextElevation) {
                    trackPoints.get(i).setTrackPointCategory(TrackPoint.TrackPointCategory.LocalMinimum);
                }
            } else if (previousElevation == currentElevation) {
                trackPoints.get(i).setTrackPointCategory(TrackPoint.TrackPointCategory.Flat);
            }
        }
        if (trackPoints.get(trackPoints.size() - 2).getElevation() > trackPoints.get(trackPoints.size() - 1).getElevation()) {
            trackPoints.get(trackPoints.size() - 1).setTrackPointCategory(TrackPoint.TrackPointCategory.LocalMinimum);
        } else {
            trackPoints.get(trackPoints.size() - 1).setTrackPointCategory(TrackPoint.TrackPointCategory.LocalMaximum);
        }
    }

    /**
     * Permet de renseigner, pour chaque TrackPoint du Track, la distance qui le sépare du TrackPoint suivant
     */
    private void acquaintDistanceBetweenTrackpoints() {
        for (int i = 0; i < trackPoints.size() - 1; i++) {
            double lat1 = trackPoints.get(i).getLatitude();
            double lat2 = trackPoints.get(i + 1).getLatitude();
            double lon1 = trackPoints.get(i).getLongitude();
            double lon2 = trackPoints.get(i + 1).getLongitude();
            trackPoints.get(i).setDistanceToNextPoint(meterDistanceBetweenPoints(lat1, lon1, lat2, lon2));
        }
    }

    /**
     * Retourne la distance entre deux TrackPoints a et b
     *
     * @param lat1 la latitude du TrackPoint a
     * @param lon1 la longitude du TrackPoint a
     * @param lat2 la latitude du TrackPoint b
     * @param lon2 la longitude du TrackPoint b
     * @return double : la valeur en mètre de la disance entre deux points
     */
    private double meterDistanceBetweenPoints(double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist *= 60 * 1.1515 * 1.609344;
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
