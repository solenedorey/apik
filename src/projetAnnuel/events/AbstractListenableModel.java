package projetAnnuel.events;

import java.util.ArrayList;

/**
 * Classe permettant à un modèle d'être écouté
 */
public abstract class AbstractListenableModel implements ListenableModel {

    /**
     * Liste des écouteurs
     */
    private ArrayList<ModelListener> listeners;

    /**
     * Constructeur
     */
    public AbstractListenableModel() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Permet l'abonnement d'un écouteur au modèle écoutable
     *
     * @param modelListener : une instance de ModelListener
     */
    public void addListener(ModelListener modelListener) {
        listeners.add(modelListener);
        modelListener.updateModel(this);
    }

    /**
     * Permet le désabonnement d'un écouteur
     */
    public void removeListener(ModelListener modelListener) {
        listeners.remove(modelListener);
    }

    /**
     * Permet de prévenir chaque écouteur que le modèle a changé
     */
    protected void fireChanges() {
        for (ModelListener modelListener : listeners)
        {
            modelListener.updateModel(this);
        }
    }
}
