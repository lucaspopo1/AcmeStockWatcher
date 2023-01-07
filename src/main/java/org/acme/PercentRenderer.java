package org.acme;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PercentRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Float percent = (Float) value;
        setText(percent +"%");

        if(percent < 0) {
            setForeground(Color.RED);
        } else {
            setForeground(Color.BLACK);
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
