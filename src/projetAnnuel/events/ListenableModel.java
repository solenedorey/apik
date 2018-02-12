package projetAnnuel.events;

public interface ListenableModel {

    void addListener(ModelListener l);

    void removeListener(ModelListener l);
}
