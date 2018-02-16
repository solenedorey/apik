package projetAnnuel.views;

import projetAnnuel.models.Track;
import projetAnnuel.models.TrackLoaderGPX;
import projetAnnuel.events.MyWindowListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.prefs.Preferences;

public class ApikGUI extends JFrame implements ActionListener {

    private JMenuItem loadGPXFileMenuItem;
    private JMenuItem quitMenuItem;

    private Track track;

    private TrackChart trackChart;
    private StatsView statsView;
    private GlobalInfoView globalInfoView;

    private JFileChooser fileChooser;
    private Preferences prefs;

    public ApikGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new MyWindowListener(this));

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        prefs = Preferences.userRoot();

        buildMenu();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        globalInfoView = new GlobalInfoView(track);
        mainPanel.add(globalInfoView, BorderLayout.NORTH);

        trackChart = new TrackChart();
        mainPanel.add(trackChart, BorderLayout.CENTER);

        statsView = new StatsView();
        mainPanel.add(statsView, BorderLayout.EAST);

        contentPane.add(mainPanel, BorderLayout.CENTER);

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
            fileChooser.setCurrentDirectory(new File(prefs.get("LastPath", fileChooser.getFileSystemView().getDefaultDirectory().toString())));
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                prefs.put("LastPath", fileChooser.getSelectedFile().getAbsolutePath());
                File file = fileChooser.getSelectedFile();
                TrackLoaderGPX trackLoaderGPX = new TrackLoaderGPX(file.getAbsolutePath());
                track = trackLoaderGPX.loadTrack();
                globalInfoView.setTrack(track);
                trackChart.setTrack(track);
                statsView.setTrack(track);
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

    public void buildMenu() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GPX files", "gpx");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        //Build the first menu.
        JMenu menu = new JMenu("Fichier");
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
        JMenu submenu = new JMenu("Unité en abscisse");

        ButtonGroup abscissaSettingsButtonGroup = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Distance");
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
    }
}
