package projetAnnuel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackLoaderGPX extends DefaultHandler implements TrackLoader {

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
    public Track loadTrack() {
        return handler.getTrack();
    }
}