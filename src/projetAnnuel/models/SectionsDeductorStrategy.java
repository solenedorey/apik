package projetAnnuel.models;

import java.util.ArrayList;

/**
 * Interface permettant d'implémenter la méthode pour déduire des sections
 */
public interface SectionsDeductorStrategy {

    /**
     * Permet de déduire des sections à partir d'un track
     * @param track Track
     * @return ArrayList<TrackSection>
     */
    ArrayList<TrackSection> deduceSections(Track track);
}
