package projetAnnuel;

public class TrackSection {

    private TrackPoint startIndex;
    private TrackPoint endIndex;
    private double averageSpeed;
    private double distanceTravelled;

    public TrackSection(TrackPoint startIndex, TrackPoint endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
}
