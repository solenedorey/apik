package projetAnnuel.models;

import projetAnnuel.events.AbstractListenableModel;

import java.util.ArrayList;

public class Track extends AbstractListenableModel {

    private SectionDeductorStrategy sectionDeductorStrategy;

    // Liste de TrackPoints
    private ArrayList<TrackPoint> trackPoints;
    // Liste des sections
    private ArrayList<TrackSection> trackSections;
    // Liste des sections dont l'altitude est croissante
    private ArrayList<TrackSection> trackRisingSections;
    // Liste des sections dont l'altitude est décroissante
    private ArrayList<TrackSection> trackDescendingSections;
    // L'altitude maximum
    private double maxElevation;
    // L'altitude minimum
    private double minElevation;
    // La distance totale parcourue
    private double totalDistance;
    // La durée totale du track
    private long totalDuration;

    /**
     * Constructor.
     */
    public Track(SectionDeductorStrategy sectionDeductorStrategy) {
        this.trackPoints = new ArrayList<>();
        this.sectionDeductorStrategy = sectionDeductorStrategy;
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
     * Retourne la liste des TrackSections
     *
     * @return une ArrayList
     */
    public ArrayList<TrackSection> getTrackSections() {
        return trackSections;
    }

    /**
     * Retourne la liste des TrackSections dont l'altitude est croissante
     *
     * @return une ArrayList
     */
    public ArrayList<TrackSection> getTrackRisingSections() {
        return trackRisingSections;
    }

    /**
     * Retourne la liste des TrackSections dont l'altitude est décroissante
     *
     * @return une ArrayList
     */
    public ArrayList<TrackSection> getTrackDescendingSections() {
        return trackDescendingSections;
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
     * @return un double
     */
    public double getTotalDistance() {
        return totalDistance;
    }

    /**
     * Retourne la durée totale du Track
     *
     * @return un long
     */
    public long getTotalDuration() {
        return totalDuration;
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
        computeTotalDuration();
        determineMaxAndMinElevation();
        trackSections = sectionDeductorStrategy.deduceSections(this);
        determineRisingAndDescendingSections();
        fireChanges();
    }

    private void determineRisingAndDescendingSections() {
        trackRisingSections = new ArrayList<>();
        trackDescendingSections = new ArrayList<>();
        for (TrackSection trackSection : getTrackSections()) {
            if (trackSection.getStartIndex().getElevation() > trackSection.getEndIndex().getElevation()) {
                trackDescendingSections.add(trackSection);
            } else {
                trackRisingSections.add(trackSection);
            }
        }
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

    /**
     * Renseigne la durée totale du track
     */
    private void computeTotalDuration() {
        totalDuration = trackPoints.get(trackPoints.size() - 1).getTime().getTime() - trackPoints.get(0).getTime().getTime();
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
        double dist = Math.sin(decimalDegreesToRadians(lat1)) * Math.sin(decimalDegreesToRadians(lat2)) + Math.cos(decimalDegreesToRadians(lat1)) * Math.cos(decimalDegreesToRadians(lat2)) * Math.cos(decimalDegreesToRadians(theta));
        dist = Math.acos(dist);
        dist = radiansToDecimalDegrees(dist);
        dist *= 60 * 1.1515 * 1609.344;
        return dist;
    }

    /**
     * Permet la conversion de degrés décimaux en radians
     *
     * @param decimalDegrees double la valeur en degrés décimaux à convertir
     * @return double : la valeur convertie en radians
     */
    private double decimalDegreesToRadians(double decimalDegrees) {
        return (decimalDegrees * Math.PI / 180.0);
    }

    /**
     * Permet la conversion de radians en degrés décimaux
     *
     * @param radians double la valeur en radians à convertir
     * @return double : la valeur convertie en degrés décimaux
     */
    private double radiansToDecimalDegrees(double radians) {
        return (radians * 180 / Math.PI);
    }
}
