package projetAnnuel;

public abstract class TrackLoader {

    // Utilisation du patron Template Method
    public Track loadTrack()
    {
        Track track = loadTrackImpl();
        track.prepare();
        return track;
    }

    public abstract Track loadTrackImpl();
}
