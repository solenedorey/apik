package projetAnnuel.events;

/**
 * Interface permettant à un modèle d'être écouté
 */
public interface ListenableModel {

    /**
     * Permet l'abonnement d'un écouteur au modèle écoutable
     *
     * @param modelListener : une instance de ModelListener
     */
    void addListener(ModelListener modelListener);

    /**
     * Permet le désabonnement d'un écouteur
     */
    void removeListener(ModelListener modelListener);
}
