package projetAnnuel;

import projetAnnuel.events.MyWindowListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ApikGUI extends JFrame implements ActionListener {

    private JButton loadFileButton;
    private Track track;
    private final JFileChooser fileChooser;

    public ApikGUI(Track track) {
        super("APIK");
        fileChooser = new JFileChooser();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new MyWindowListener(this));

        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        loadFileButton = new JButton("Load track");
        loadFileButton.addActionListener(this);

        cp.add(loadFileButton, BorderLayout.NORTH);

        if (track != null) {
            TrackChart trackChart = new TrackChart(track);
            cp.add(trackChart);
        } else {
            cp.add(new JLabel("Aucun track n'a été chargé."));
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*if (e.getSource() == loadFileButton) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Opening: " + file.getAbsolutePath());
                TrackLoaderGPX trackLoaderGPX = new TrackLoaderGPX(file.getAbsolutePath());
                track = trackLoaderGPX.loadTrack();
            } else {
                System.out.println("Open command cancelled by user");
            }
        }*/
    }
}
