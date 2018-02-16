package projetAnnuel.models;

import java.awt.*;

/**
 * Classe permettant la création d'un point enrichi d'un label
 */
public class ChartPoint extends Point {

    /**
     * Le label du point
     */
    private String label;

    /**
     * Constructeur
     *
     * @param x int : abscisse du point
     * @param y int : ordonnée du point
     * @param label String : le label du point
     */
    public ChartPoint(int x, int y, String label) {
        super(x, y);
        this.label = label;
    }

    /**
     * Permet de récupérer le label du point
     *
     * @return String : le label du point
     */
    public String getLabel() {
        return label;
    }

    /**
     * Permet de modifier le label du point
     *
     * @param label String : nouvelle valeur du label
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
