package projetAnnuel;

import java.awt.*;

public class ChartPoint extends Point {

    private String label;

    public ChartPoint(int x, int y, String label) {
        super(x, y);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
