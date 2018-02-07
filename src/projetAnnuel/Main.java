package projetAnnuel;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        /*SAXParserFactory factory = SAXParserFactory.newInstance();
        SaxGPSData handler = new SaxGPSData();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse("ProjetAnnuel/ski231206.gpx", handler);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        TrackLoaderGPX trackLoaderGPX = new TrackLoaderGPX("ProjetAnnuel/ski231206.gpx");
        Track track = trackLoaderGPX.loadTrack();

        System.out.println(track);
        //System.out.println(track.getMinAndMaxLocalPeaks().size());

        /*ApikGUI apikGUI = new ApikGUI(track);*/

        /*ApikGUI apikGUI = new ApikGUI();*/

        /*TrackChart trackChart = new TrackChart(track.getTrackPoints());*/
    }
}
