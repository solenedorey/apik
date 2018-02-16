package projetAnnuel.events;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe permettant de gérer les événements liés à la fenêtre
 */
public class MyWindowListener extends WindowAdapter {

    /**
     * La fenêtre écoutée
     */
    private JFrame jFrame;

    /**
     * Constructeur
     *
     * @param jFrame : une instance de JFrame
     */
    public MyWindowListener(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    /**
     * Permet de gérer le comportement au clic sur la croix de la fenêtre
     *
     * @param windowEvent
     */
    public void windowClosing(WindowEvent windowEvent) {
        String message = "Voulez-vous vraiment quitter l'application " + jFrame.getTitle() + " ?";
        String title = "Demande de confirmation";
        int choice = JOptionPane.showConfirmDialog(jFrame, message, title, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
