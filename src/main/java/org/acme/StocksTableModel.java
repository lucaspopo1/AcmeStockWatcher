package org.acme;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

public class StocksTableModel implements TableModel {

    private List<List<String>> model;

    public StocksTableModel(List<List<String>> model) {
        this.model = model;
    }

    @Override
    public int getRowCount() {
        return model.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return model.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
