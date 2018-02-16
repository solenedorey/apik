package projetAnnuel.models;

import java.util.ArrayList;

/**
 * Classe représentant une section d'un track
 */
public class TrackSection {

    private Track track;
    private TrackPoint startIndex;
    private TrackPoint endIndex;
    private double distanceTravelled;
    private long spentTime;

    /**
     * Constructeur
     *
     * @param track Track
     * @param startIndex TrackPoint
     * @param endIndex TrackPoint
     */
    public TrackSection(Track track, TrackPoint startIndex, TrackPoint endIndex) {
        this.track = track;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        computeDistanceTravelled();
        computeSpentTime();
    }

    /**
     * Permet de calculer le temps écoulé entre le point de départ et le point de fin de la section
     */
    private void computeSpentTime() {
        spentTime = endIndex.getTime().getTime() - startIndex.getTime().getTime();
    }

    /**
     * Permet de calculer la distance parcourue entre le point de départ et le point de fin de la section
     */
    private void computeDistanceTravelled() {
        ArrayList<TrackPoint> trackPoints = track.getTrackPoints();
        boolean isInSection = false;
        distanceTravelled = 0;
        for (int i = 0; i < trackPoints.size(); i++) {
            if (trackPoints.get(i) == endIndex) {
                break;
            }
            if (trackPoints.get(i) == startIndex) {
                isInSection = true;
            }
            if (isInSection) {
                distanceTravelled += trackPoints.get(i).getDistanceToNextPoint();
            }
        }
    }

    /**
     * Permet de retourner la vitesse moyenne en km par heure sur la section
     *
     * @return double
     */
    public double getAverageSpeedInKmPerHour() {
        return getAverageSpeedInMetersPerSecond() * 3.6;
    }

    /**
     * Permet de retourner la vitesse moyenne en m par seconde sur la section
     *
     * @return double
     */
    public double getAverageSpeedInMetersPerSecond() {
        long spentTimeInSeconds = spentTime / 1000;
        return distanceTravelled / spentTimeInSeconds;
    }

    /**
     * Getters
     */

    public TrackPoint getStartIndex() {
        return startIndex;
    }

    public TrackPoint getEndIndex() {
        return endIndex;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public long getSpentTime() {
        return spentTime;
    }
}
