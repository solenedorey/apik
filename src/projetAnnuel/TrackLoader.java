package projetAnnuel;

public abstract class TrackLoader {

    // Utilisation du patron Template Method
    public Track loadTrack()
    {
        Track t = loadTrackImpl();
        t.prepare();
        return t;
    }

    public abstract Track loadTrackImpl();
}
