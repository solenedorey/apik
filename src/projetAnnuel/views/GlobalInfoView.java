package projetAnnuel.views;

import projetAnnuel.events.ModelListener;
import projetAnnuel.models.Track;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalInfoView extends JPanel implements ModelListener {

    private Track track;
    private JLabel totalDistance;
    private JLabel totalTime;
    private JLabel descentsNumber;
    private JLabel ascentsNumber;
    private JLabel maxElevation;
    private JLabel minElevation;

    public GlobalInfoView(Track track) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Informations globales",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        this.track = track;
        setLayout(new GridLayout(2,3));
        totalDistance = new JLabel("Distance totale en km : -");
        totalTime = new JLabel("Durée totale : -");
        descentsNumber = new JLabel("Nombre de descentes : -");
        ascentsNumber = new JLabel("Nombre de montées : -");
        maxElevation = new JLabel("Altitude maximale en m : -");
        minElevation = new JLabel("Altitude minimale en m : -");
        add(totalDistance);
        add(descentsNumber);
        add(maxElevation);
        add(totalTime);
        add(ascentsNumber);
        add(minElevation);
    }

    public void setTrack(Track track) {
        if (this.track != null) {
            track.removeListener(this);
        }
        this.track = track;
        track.addListener(this);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date duration = new Date(track.getTotalDuration());
        totalDistance.setText("Distance totale en km : " + String.valueOf(decimalFormat.format(track.getTotalDistance() / 1000)));
        totalTime.setText("Durée totale : " + dateFormat.format(duration));
        descentsNumber.setText("Nombre de descentes : " + String.valueOf(track.getTrackDescendingSections().size()));
        ascentsNumber.setText("Nombre de montées : " + String.valueOf(track.getTrackRisingSections().size()));
        maxElevation.setText("Altitude maximale en m : " + String.valueOf((int) track.getMaxElevation()));
        minElevation.setText("Altitude minimale en m: " + String.valueOf((int) track.getMinElevation()));
        repaint();
    }

    @Override
    public void updateModel(Object object) {
        repaint();
    }
}
