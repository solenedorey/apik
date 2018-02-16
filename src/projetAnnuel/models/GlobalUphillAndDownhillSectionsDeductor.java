package projetAnnuel.models;

import java.util.ArrayList;

public class GlobalUphillAndDownhillSectionsDeductor implements SectionsDeductorStrategy {

    private final static double METER_RANGE = 400.0;

    /**
     * Permet la déduction des sections montées et descentes globales du track
     * @param track Track
     * @return ArrayList de TrackSection
     */
    @Override
    public ArrayList<TrackSection> deduceSections(Track track) {
        // La liste des points à altitude minimum et maximum globaux
        ArrayList<TrackPoint> tempTrackPointSections = new ArrayList<>();
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
            // Tant que l'on a pas parcouru les TrackPoints dans la fourchette donnée...
            while (distanceBefore <= METER_RANGE) {
                if (j < 0) {
                    break;
                }
                // ... on vérifie si 'il y a un point plus élevé que nous
                if (!isThereSomeoneHigherThanMeBefore && trackPoints.get(j).getElevation() >= currentElevation) {
                    isThereSomeoneHigherThanMeBefore = true;
                }
                // ... on vérifie si 'il y a un point moins élevé que nous
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
                // ... on vérifie si 'il y a un point plus élevé que nous
                if (!isThereSomeoneHigherThanMeAfter && trackPoints.get(k).getElevation() > currentElevation) {
                    isThereSomeoneHigherThanMeAfter = true;
                }
                // ... on vérifie si 'il y a un point moins élevé que nous
                if (!isThereSomeoneLowerThanMeAfter && trackPoints.get(k).getElevation() <= currentElevation) {
                    isThereSomeoneLowerThanMeAfter = true;
                }
                distanceAfter += trackPoints.get(k).getDistanceToNextPoint();
                k++;
            }
            if (!isThereSomeoneHigherThanMeBefore && !isThereSomeoneHigherThanMeAfter) {
                // S'il y a personne au dessus de moi dans la fourchette donnée
                // Alors je suis un maximum global
                tempTrackPointSections.add(trackPoints.get(i));
            } else if (!isThereSomeoneLowerThanMeBefore && !isThereSomeoneLowerThanMeAfter) {
                // S'il y a personne en dessous de moi dans la fourchette donnée
                // Alors je suis un minimum global
                tempTrackPointSections.add(trackPoints.get(i));
            }
        }
        // A partir de la liste des points minimum et maximum globaux des sections sont crées
        ArrayList<TrackSection> trackSections = new ArrayList<>();
        for (int i = 0; i < tempTrackPointSections.size() - 1; i++) {
            TrackSection trackSection = new TrackSection(track, tempTrackPointSections.get(i), tempTrackPointSections.get(i + 1));
            trackSections.add(trackSection);
        }
        return trackSections;
    }
}
