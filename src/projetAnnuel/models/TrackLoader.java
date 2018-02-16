package projetAnnuel.models;

/**
 * Classe abstraite permettant de définir la façon dont charger un track
 */
public abstract class TrackLoader {

    // Utilisation du patron de conception Template Method
    public Track loadTrack()
    {
        Track track = loadTrackImpl();
        track.prepare();
        return track;
    }

    public abstract Track loadTrackImpl();
}
