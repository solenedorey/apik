package projetAnnuel.models;

import org.xml.sax.SAXException;
import projetAnnuel.Main;
import projetAnnuel.models.GPXHandler;
import projetAnnuel.models.Track;
import projetAnnuel.models.TrackLoader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackLoaderGPX extends TrackLoader {

    private GPXHandler handler;

    public TrackLoaderGPX(String filePath) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        handler = new GPXHandler();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(filePath, handler);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Track loadTrackImpl() {
        return handler.getTrack();
    }
}
