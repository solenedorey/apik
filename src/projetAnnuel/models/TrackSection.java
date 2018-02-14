package projetAnnuel.models;

import projetAnnuel.models.Track;
import projetAnnuel.models.TrackPoint;

import java.util.ArrayList;

public class TrackSection {

    private Track track;
    private TrackPoint startIndex;
    private TrackPoint endIndex;
    private double distanceTravelled;
    private long spentTime;

    public TrackSection(Track track, TrackPoint startIndex, TrackPoint endIndex) {
        this.track = track;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        computeDistanceTravelled();
        computeSpentTime();
    }

    private void computeSpentTime() {
        spentTime = endIndex.getTime().getTime() - startIndex.getTime().getTime();
    }

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

    public double getAverageSpeedInKmPerHour() {
        return getAverageSpeedInMetersPerSecond() * 3.6;
    }

    public double getAverageSpeedInMetersPerSecond() {
        long spentTimeInSeconds = spentTime / 1000;
        return distanceTravelled / spentTimeInSeconds;
    }

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
