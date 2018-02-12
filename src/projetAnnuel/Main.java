package projetAnnuel;

public class Main {
    public static void main(String[] args) {

        TrackLoaderGPX trackLoaderGPX = new TrackLoaderGPX("ProjetAnnuel/ski231206.gpx");
        Track track = trackLoaderGPX.loadTrack();

        /*System.out.println(track);*/

        ApikGUI apikGUI = new ApikGUI(track);

        /*ApikGUI apikGUI = new ApikGUI();*/
    }
}
