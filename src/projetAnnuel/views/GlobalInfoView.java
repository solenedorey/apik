package projetAnnuel.views;

import projetAnnuel.models.Track;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalInfoView extends JPanel {

    private Track track;
    private JLabel noTrackLabel;

    public GlobalInfoView() {
        setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Informations globales",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        this.track = track;
        setLayout(new BorderLayout());
        noTrackLabel = new JLabel("Aucun track n'a été chargé.");
        noTrackLabel.setHorizontalAlignment(JLabel.CENTER);
        add(noTrackLabel, BorderLayout.CENTER);
    }

    public void setTrack(Track track) {
        remove(noTrackLabel);
        setLayout(new GridLayout(2,3));
        this.track = track;
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        JLabel totalDistance = new JLabel("Distance totale en km : " + String.valueOf(decimalFormat.format(track.getTotalDistance() / 1000)));
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date duration = new Date(track.getTotalDuration());
        JLabel totalTime = new JLabel("Durée totale : " + dateFormat.format(duration));
        JLabel descentsNumber = new JLabel("Nombre de descentes : " + String.valueOf(track.getTrackDescendingSections().size()));
        JLabel ascentsNumber = new JLabel("Nombre de montées : " + String.valueOf(track.getTrackRisingSections().size()));
        JLabel maxElevation = new JLabel("Altitude maximale : " + String.valueOf(track.getMaxElevation()));
        JLabel minElevation = new JLabel("Altitude minimale : " + String.valueOf(track.getMinElevation()));
        add(totalDistance);
        add(descentsNumber);
        add(maxElevation);
        add(totalTime);
        add(ascentsNumber);
        add(minElevation);
    }
}
