package projetAnnuel;

import java.util.ArrayList;

public class OptimumSectionDeductor implements SectionDeductorStrategy {

    @Override
    public ArrayList<TrackPoint> deduceSections(Track track) {
        /*ArrayList<TrackSection> trackSections = new ArrayList<>();*/
        ArrayList<TrackPoint> tempTrackSections = new ArrayList<>();
        /*TrackPoint tempTrackPointMax;
        TrackPoint tempTrackPointMin;*/
        ArrayList<TrackPoint> trackPoints = track.getTrackPoints();
        for (int i = 0; i < trackPoints.size(); i++) {
            double currentElevation = trackPoints.get(i).getElevation();
            double distanceBefore = 0;
            double distanceAfter = 0;
            boolean isThereSomeoneHigherThanMeBefore = false;
            boolean isThereSomeoneLowerThanMeBefore = false;
            boolean isThereSomeoneHigherThanMeAfter = false;
            boolean isThereSomeoneLowerThanMeAfter = false;
            int j = i - 1;
            while (distanceBefore <= 100.0) {
                if (j < 0) {
                    break;
                }
                if (!isThereSomeoneHigherThanMeBefore && trackPoints.get(j).getElevation() >= currentElevation) {
                    isThereSomeoneHigherThanMeBefore = true;
                }
                if (!isThereSomeoneLowerThanMeBefore && trackPoints.get(j).getElevation() <= currentElevation) {
                    isThereSomeoneLowerThanMeBefore = true;
                }
                /*if (isThereSomeoneLowerThanMe && !isThereSomeoneHigherThanMe) {
                    *//*tempListMax.add(trackPoints.get(j));*//*
                    tempTrackPointMax = trackPoints.get(i);
                    break;
                }*/
                /*if (!isThereSomeoneLowerThanMe && isThereSomeoneHigherThanMe) {
                    *//*tempListMin.add(trackPoints.get(j));*//*
                    tempTrackPointMin = trackPoints.get(i);
                    break;
                }*/
                distanceBefore += trackPoints.get(j).getDistanceToNextPoint();
                j--;
            }
            /*if (j > 0 && distanceBefore > 300.0) {
                tempList.remove(tempList.get(tempList.size() - 1));
            }*/
            int k = i + 1;
            while (distanceAfter <= 100.0) {
                if (k >= trackPoints.size()) {
                    break;
                }
                if (!isThereSomeoneHigherThanMeAfter && trackPoints.get(k).getElevation() >= currentElevation) {
                    isThereSomeoneHigherThanMeAfter = true;
                }
                if (!isThereSomeoneLowerThanMeAfter && trackPoints.get(k).getElevation() <= currentElevation) {
                    isThereSomeoneLowerThanMeAfter = true;
                }
                distanceAfter += trackPoints.get(k).getDistanceToNextPoint();
                k++;
            }
            if (!isThereSomeoneHigherThanMeBefore && !isThereSomeoneHigherThanMeAfter) {
                tempTrackSections.add(trackPoints.get(i));
            } else if (!isThereSomeoneLowerThanMeBefore && !isThereSomeoneLowerThanMeAfter) {
                tempTrackSections.add(trackPoints.get(i));
            }
            /*if (distanceAfter > 50.0) {
                tempList.remove(tempList.get(tempList.size() - 1));
            }*/
            System.out.println(distanceBefore);
            System.out.println(distanceAfter);
            System.out.println("----");
        }
        System.out.println(tempTrackSections);
        System.out.println(tempTrackSections.size());
        /*return trackSections;*/
        return tempTrackSections;
    }
}
