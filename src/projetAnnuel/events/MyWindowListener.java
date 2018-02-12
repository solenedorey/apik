package projetAnnuel.events;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyWindowListener extends WindowAdapter {

    private JFrame jf;

    public MyWindowListener(JFrame jf) {
        this.jf = jf;
    }

    public void windowClosing(WindowEvent e) {
        String message = "Voulez-vous vraiment quitter l'application " + jf.getTitle() + " ?";
        String title = "Demande de confirmation";
        int choice = JOptionPane.showConfirmDialog(jf, message, title, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
