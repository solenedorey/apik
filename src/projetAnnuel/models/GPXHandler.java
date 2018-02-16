package projetAnnuel.models;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Classe permettant le parsing d'un fichier GPX
 */
public class GPXHandler extends DefaultHandler {

    private PathXML tagsStack = new PathXML();
    private PathXML trackPointTag = new PathXML();
    private PathXML elevationTag = new PathXML();
    private PathXML timeTag = new PathXML();
    private String TagContent = "";
    private TrackPoint trackPoint;
    private Track track;

    /**
     * Constructeur
     */
    public GPXHandler() {
        elevationTag.push("ele");
        timeTag.push("trkpt");
        timeTag.push("time");
        trackPointTag.push("trkpt");
        track = new Track(new OptimumSectionDeductor());
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
        tagsStack.push(qName);
        if (tagsStack.endsWith(trackPointTag)) {
            trackPoint = new TrackPoint();
            for (int i = 0; i < atts.getLength(); i++) {
                if (atts.getQName(i).equals("lat")) {
                    trackPoint.setLatitude(Double.parseDouble(atts.getValue(i)));
                }
                if (atts.getQName(i).equals("lon")) {
                    trackPoint.setLongitude(Double.parseDouble(atts.getValue(i)));
                }
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) {
        if (tagsStack.endsWith(elevationTag)) {
            if (!TagContent.equals("")) {
                trackPoint.setElevation(Double.parseDouble(TagContent));
            }
        }
        if (tagsStack.endsWith(timeTag)) {
            if (!TagContent.equals("")) {
                trackPoint.setTime(javax.xml.bind.DatatypeConverter.parseDateTime(TagContent).getTime());
            }
        }
        if (tagsStack.endsWith(trackPointTag)) {
            track.addTrackPoint(trackPoint);
        }
        TagContent = "";
        tagsStack.pop();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        TagContent += new String(ch, start, length).trim();
    }

    public Track getTrack() {
        return track;
    }

}
