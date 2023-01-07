package org.acme;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CapRenderer extends JLabel implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        try {
            double cap = (double) value;
            String render = truncateNumber(cap);
            setText("$ " + render);
        } catch (Throwable e) {
            throw e;
        }
        if(isSelected) {
            setOpaque(true);
            setBackground(table.getSelectionBackground());
        } else {
            setOpaque(false);
            setBackground(table.getBackground());
        }
        return this;
    }

    public String truncateNumber(double number) {
        double million = 1000000L;
        double billion = 1000000000L;
        double trillion = 1000000000000L;
        if ((number >= million) && (number < billion)) {
            double fraction = number/million;
            return fraction + "M";
        } else if ((number >= billion) && (number < trillion)) {
            double fraction = (number/million)/1000;
            return fraction + "B";
        }
        return Double.toString(number);
    }

}
