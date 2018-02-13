package projetAnnuel;

import projetAnnuel.events.MyWindowListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class ApikGUI extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem menuItem;

    private JMenuItem loadGPXFileMenuItem;
    private JMenuItem quitMenuItem;

    private JRadioButtonMenuItem rbMenuItem;
    private Track track;
    private TrackChart trackChart;
    private final JFileChooser fileChooser;

    public ApikGUI(Track track, String title) {
        super(title);
        fileChooser = new JFileChooser();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new MyWindowListener(this));

        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Fichier");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription("Actions basiques");
        menuBar.add(menu);

        //a group of JMenuItems
        loadGPXFileMenuItem = new JMenuItem("Charger fichier GPX",
                KeyEvent.VK_L);
        loadGPXFileMenuItem.getAccessibleContext().setAccessibleDescription(
                "Charger un fichier GPX");
        loadGPXFileMenuItem.addActionListener(this);
        menu.add(loadGPXFileMenuItem);

        menu.addSeparator();

        quitMenuItem = new JMenuItem("Quitter",
                KeyEvent.VK_Q);
        quitMenuItem.getAccessibleContext().setAccessibleDescription(
                "Quitter l'application");
        quitMenuItem.addActionListener(this);
        menu.add(quitMenuItem);

        //Build second menu in the menu bar.
        menu = new JMenu("Configurations");
        menu.setMnemonic(KeyEvent.VK_S);
        menu.getAccessibleContext().setAccessibleDescription(
                "Configurations du graphique");

        //a submenu
        submenu = new JMenu("Unité en abscisse");

        ButtonGroup abscissaSettingsButtonGroup = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Distance");
        rbMenuItem.setSelected(true);
        abscissaSettingsButtonGroup.add(rbMenuItem);
        submenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Temps");
        abscissaSettingsButtonGroup.add(rbMenuItem);
        submenu.add(rbMenuItem);

        menu.add(submenu);

        menu.addSeparator();

        //a submenu
        submenu = new JMenu("Origine en ordonnée");

        ButtonGroup ordinateSettingsButtonGroup = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Niveau de la mer");
        rbMenuItem.setSelected(true);
        ordinateSettingsButtonGroup.add(rbMenuItem);
        submenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Altitude minimale station");
        ordinateSettingsButtonGroup.add(rbMenuItem);
        submenu.add(rbMenuItem);

        menu.add(submenu);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        trackChart = new TrackChart();
        cp.add(trackChart);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadGPXFileMenuItem) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Opening: " + file.getAbsolutePath());
                TrackLoaderGPX trackLoaderGPX = new TrackLoaderGPX(file.getAbsolutePath());
                track = trackLoaderGPX.loadTrack();
                trackChart.setTrack(track);
            } else {
                System.out.println("Open command cancelled by user");
            }
        }
        if (e.getSource() == quitMenuItem) {
            String message = "Voulez-vous vraiment quitter l'application " + getTitle() + " ?";
            String title = "Demande de confirmation";
            int choice = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
