package projetAnnuel.views;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Classe permettant le rendu sur plusieurs lignes des headers d'un tableau
 */
public class MultiLineTableHeaderRenderer extends JTextArea implements TableCellRenderer
{
    public MultiLineTableHeaderRenderer() {
        setEditable(false);
        setLineWrap(true);
        setOpaque(false);
        setFocusable(false);
        setWrapStyleWord(true);
        LookAndFeel.installBorder(this, "TableHeader.cellBorder");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        int width = table.getColumnModel().getColumn(column).getWidth();
        setText((String)value);
        setSize(width, getPreferredSize().height);
        return this;
    }
}
