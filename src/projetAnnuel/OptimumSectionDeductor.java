package projetAnnuel;

import java.util.ArrayList;

public class OptimumSectionDeductor implements SectionDeductorStrategy {

    private final static double METER_RANGE = 400.0;

    @Override
    public ArrayList<TrackSection> deduceSections(Track track) {
        ArrayList<TrackPoint> tempTrackPointSections = new ArrayList<>();
        ArrayList<TrackPoint> trackPoints = track.getTrackPoints();
        /*tempTrackPointSections.add(trackPoints.get(0));*/
        for (int i = 0; i < trackPoints.size(); i++) {
            double currentElevation = trackPoints.get(i).getElevation();
            double distanceBefore = 0;
            double distanceAfter = 0;
            boolean isThereSomeoneHigherThanMeBefore = false;
            boolean isThereSomeoneLowerThanMeBefore = false;
            boolean isThereSomeoneHigherThanMeAfter = false;
            boolean isThereSomeoneLowerThanMeAfter = false;
            int j = i - 1;
            while (distanceBefore <= METER_RANGE) {
                if (j < 0) {
                    break;
                }
                if (!isThereSomeoneHigherThanMeBefore && trackPoints.get(j).getElevation() >= currentElevation) {
                    isThereSomeoneHigherThanMeBefore = true;
                }
                if (!isThereSomeoneLowerThanMeBefore && trackPoints.get(j).getElevation() < currentElevation) {
                    isThereSomeoneLowerThanMeBefore = true;
                }
                distanceBefore += trackPoints.get(j).getDistanceToNextPoint();
                j--;
            }
            int k = i + 1;
            while (distanceAfter <= METER_RANGE) {
                if (k >= trackPoints.size()) {
                    break;
                }
                if (!isThereSomeoneHigherThanMeAfter && trackPoints.get(k).getElevation() > currentElevation) {
                    isThereSomeoneHigherThanMeAfter = true;
                }
                if (!isThereSomeoneLowerThanMeAfter && trackPoints.get(k).getElevation() <= currentElevation) {
                    isThereSomeoneLowerThanMeAfter = true;
                }
                distanceAfter += trackPoints.get(k).getDistanceToNextPoint();
                k++;
            }
            if (!isThereSomeoneHigherThanMeBefore && !isThereSomeoneHigherThanMeAfter) {
                tempTrackPointSections.add(trackPoints.get(i));
            } else if (!isThereSomeoneLowerThanMeBefore && !isThereSomeoneLowerThanMeAfter) {
                tempTrackPointSections.add(trackPoints.get(i));
            }
            /*System.out.println(distanceBefore);
            System.out.println(distanceAfter);
            System.out.println("----");*/
        }
        /*tempTrackPointSections.add(trackPoints.get(trackPoints.size() - 1));*/
        /*System.out.println(tempTrackPointSections);
        System.out.println(tempTrackPointSections.size());*/
        ArrayList<TrackSection> trackSections = new ArrayList<>();
        /*return trackSections;*/
        for (int i = 0; i < tempTrackPointSections.size() - 1; i++) {
            TrackSection trackSection = new TrackSection(track, tempTrackPointSections.get(i), tempTrackPointSections.get(i + 1));
            trackSections.add(trackSection);
        }
        return trackSections;
    }
}
