package org.acme;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RatioRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Float ratio = (Float) value;
        if(ratio == 0) {
            setText("--");
        } else {
            setText(ratio+"x");
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
}
