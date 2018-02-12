package projetAnnuel.events;

import java.util.ArrayList;

public abstract class AbstractListenableModel implements ListenableModel{

    private ArrayList<ModelListener> listeners;

    public AbstractListenableModel() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(ModelListener l) {
        listeners.add(l);
        l.updateModel(this);
    }

    public void removeListener(ModelListener l) {
        listeners.remove(l);
    }

    protected void fireChanges() {
        for (ModelListener l : listeners)
        {
            l.updateModel(this);
        }
    }
}
