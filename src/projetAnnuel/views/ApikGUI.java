package projetAnnuel.views;

import projetAnnuel.models.Track;
import projetAnnuel.models.TrackLoaderGPX;
import projetAnnuel.events.MyWindowListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

public class ApikGUI extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem menuItem;

    private JMenuItem loadGPXFileMenuItem;
    private JMenuItem quitMenuItem;

    private JRadioButtonMenuItem rbMenuItem;
    private Track track;

    private TrackChart trackChart;
    private StatsView statsViewOnDescents;
    private StatsView statsViewOnAscents;
    private GlobalInfoView globalInfoView;

    private final JFileChooser fileChooser;

    public ApikGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new MyWindowListener(this));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("GPX files", "gpx");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

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
        menu.setMnemonic(KeyEvent.VK_C);
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

        globalInfoView = new GlobalInfoView();
        cp.add(globalInfoView, BorderLayout.NORTH);

        trackChart = new TrackChart();
        cp.add(trackChart, BorderLayout.CENTER);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2,1));
        statsPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Statistiques",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        JPanel descentsPanel = new JPanel();
        descentsPanel.setLayout(new BorderLayout());
        JLabel descentsLabel = new JLabel("Descentes");
        descentsLabel.setForeground(Color.BLUE);
        descentsPanel.add(descentsLabel, BorderLayout.NORTH);
        statsViewOnDescents = new StatsView(true);
        descentsPanel.add(statsViewOnDescents, BorderLayout.CENTER);

        statsPanel.add(descentsPanel);

        JPanel ascentsPanel = new JPanel();
        ascentsPanel.setLayout(new BorderLayout());
        JLabel ascentsLabel = new JLabel("Montées");
        ascentsLabel.setForeground(Color.RED);
        ascentsPanel.add(ascentsLabel, BorderLayout.NORTH);
        statsViewOnAscents = new StatsView(false);
        ascentsPanel.add(statsViewOnAscents, BorderLayout.CENTER);

        statsPanel.add(ascentsPanel);

        cp.add(statsPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        URL url = ClassLoader.getSystemResource("projetAnnuel/views/logo.png");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(url);
        setIconImage(image);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadGPXFileMenuItem) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                TrackLoaderGPX trackLoaderGPX = new TrackLoaderGPX(file.getAbsolutePath());
                track = trackLoaderGPX.loadTrack();
                globalInfoView.setTrack(track);
                trackChart.setTrack(track);
                statsViewOnDescents.setTrack(track);
                statsViewOnAscents.setTrack(track);
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
