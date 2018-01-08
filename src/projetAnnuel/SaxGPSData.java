package projetAnnuel;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;

public class SaxGPSData extends DefaultHandler {
    private String s = "";
    private int trackPointNb = 0;
    private PathXML lifo = new PathXML();
    private PathXML trackSegmentTag = new PathXML();
    private PathXML trackPointTag = new PathXML();
    private PathXML elevationTag = new PathXML();
    private PathXML timeTag = new PathXML();
    private double[] compareTab = new double[3];
    private double previousPreviousElevation;
    private double previousElevation;
    private double currentElevation;

    public SaxGPSData() {
        elevationTag.push("ele");
        timeTag.push("time");
        trackPointTag.push("trkpt");
        trackSegmentTag.push("trkseg");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
        lifo.push(qName);
        if (lifo.endsWith(trackSegmentTag) || lifo.endsWith(trackPointTag) || lifo.endsWith(elevationTag) || lifo.endsWith(timeTag)) {
            System.out.println("// " + qName + " //");
        }
        TrackPoint trackPoint;
        if (lifo.endsWith(trackPointTag)) {
            trackPoint = new TrackPoint();
            String result = "";
            for (int i = 0; i < atts.getLength(); i++) {
                if (atts.getQName(i).equals("lat")) {
                    trackPoint.setLatitude(Double.parseDouble(atts.getValue(i)));
                }
                if (atts.getQName(i).equals("lon")) {
                    trackPoint.setLatitude(Double.parseDouble(atts.getValue(i)));
                }
                result += atts.getQName(i) + " = \"" + atts.getValue(i) + "\"\n";
            }
            System.out.println(result);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) {
        if (lifo.endsWith(elevationTag)) {
            previousPreviousElevation = previousElevation;
            previousElevation = currentElevation;
            if (!s.equals("")) {
                currentElevation = Double.parseDouble(s);
                System.out.println(s);
            }
            compareTab[0] = previousPreviousElevation;
            compareTab[1] = previousElevation;
            compareTab[2] = currentElevation;
            if ((compareTab[1] < compareTab[0] && compareTab[1] < compareTab[2]) || (compareTab[1] > compareTab[0] && compareTab[1] > compareTab[2]) ) {
                System.out.println("[" + compareTab[0] + ", " + compareTab[1] + ", " + compareTab[2] + "]");
            }
            //System.out.println("[" + compareTab[0] + ", " + compareTab[1] + ", " + compareTab[2] + "]");
        }
        if (lifo.endsWith(timeTag)) {
            if (!s.equals("")) {
                Calendar newTime = javax.xml.bind.DatatypeConverter.parseDateTime(s);
                System.out.println(newTime.getTime());
            }
        }
        if (lifo.endsWith(trackPointTag)) {
            trackPointNb++;
        }
        s = "";
        lifo.pop();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        s += new String(ch, start, length).trim();
    }

    @Override
    public void endDocument() {
        System.out.println("Nombre total de trackPoint = " + trackPointNb);
    }
}
