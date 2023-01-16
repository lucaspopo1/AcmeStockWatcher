package org.acme;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StockList {
    public static final String ALL_INDUSTRIES = "All industries";
    public static final String NOT_SET = "Not set";
    private JPanel mainStockListPanel;
    private JTable stocksTable;
    private JScrollPane stockScrollPanel;
    private JComboBox<Object> industrySelector;
    private JTextField companyNameFilter;
    private JPanel dialPanel;
    private JComboBox<FilterComboItem<Double>> mktCapComboBox;
    private JComboBox<FilterComboItem<Float>> priorCloseComboBox;
    private JComboBox<FilterComboItem<Float>> peRatioComboBox;
    private JComboBox<FilterComboItem<Float>> revenueGrowthComboBox;
    private JComboBox<FilterComboItem<Float>> returnonEquityComboBox;
    private JComboBox<FilterComboItem<Float>> epsGrowthComboBox;
    private JComboBox<FilterComboItem<Float>> dividendYieldComboBox;
    private TableRowSorter<TableModel> sorter;
    private DefaultValueDataset data;

    public void setData(Object[][] stocksData) {

        mktCapComboBox.addItem(new FilterComboItem<>(NOT_SET, d -> true));
        mktCapComboBox.addItem(new FilterComboItem<>("> 10 Bln $", d -> d/1000000 > 10000.0));
        mktCapComboBox.addItem(new FilterComboItem<>("2 < x < 10 Bln $", d -> d/1000000>2000.0 && d/1000000<10000.0));
        mktCapComboBox.addItem(new FilterComboItem<>("1 < x < 2 Bln $", d -> d/1000000>1000.0 && d/1000000<2000.0));
        mktCapComboBox.addItem(new FilterComboItem<>("< 1 Bln $", d -> d/1000000 < 1000.0));
        mktCapComboBox.addActionListener(e -> setFilter());

        priorCloseComboBox.addItem(new FilterComboItem<>(NOT_SET, d -> true));
        priorCloseComboBox.addItem(new FilterComboItem<>("> 100", d-> d > 100.0));
        priorCloseComboBox.addItem(new FilterComboItem<>("70 < x < 100", d-> 70.0 < d && d < 100.0));
        priorCloseComboBox.addItem(new FilterComboItem<>("50 < x < 70", d-> 50.0 < d && d < 70.0));
        priorCloseComboBox.addItem(new FilterComboItem<>("10 < x < 30", d-> 10.0 < d && d < 30.0));
        priorCloseComboBox.addItem(new FilterComboItem<>("< 10", d-> d < 10.0));
        priorCloseComboBox.addActionListener(e -> setFilter());

        peRatioComboBox.addItem(new FilterComboItem<>(NOT_SET, d -> true));
        peRatioComboBox.addItem(new FilterComboItem<>(">100x",d-> d > 100.0));
        peRatioComboBox.addItem(new FilterComboItem<>("50x < y < 100x",d-> d > 50.0 && d < 100.0));
        peRatioComboBox.addItem(new FilterComboItem<>("30x < y < 50x",d-> d > 30.0 && d < 50.0));
        peRatioComboBox.addItem(new FilterComboItem<>("20x < y < 30x",d-> d > 20.0 && d < 30.0));
        peRatioComboBox.addItem(new FilterComboItem<>("10x < y < 20x",d-> d > 10.0 && d < 20.0));
        peRatioComboBox.addItem(new FilterComboItem<>("10x<",d-> d < 10.0));
        peRatioComboBox.addActionListener(e -> setFilter());

        revenueGrowthComboBox.addItem(new FilterComboItem<>(NOT_SET, d -> true));
        revenueGrowthComboBox.addItem(new FilterComboItem<>(">300%", d -> d > 300.0));
        revenueGrowthComboBox.addItem(new FilterComboItem<>(">200%", d -> d > 200.0));
        revenueGrowthComboBox.addItem(new FilterComboItem<>(">100%", d -> d > 100.0));
        revenueGrowthComboBox.addItem(new FilterComboItem<>("50% < x < 100%", d -> d > 50.0 && d < 100.0));
        revenueGrowthComboBox.addItem(new FilterComboItem<>("0% < x < 50%", d -> d > 0 && d < 50.0));
        revenueGrowthComboBox.addItem(new FilterComboItem<>("-50% < x < 0%", d -> d > -50.0 && d < 0));
        revenueGrowthComboBox.addItem(new FilterComboItem<>("<-50%", d -> d < -50.0));
        revenueGrowthComboBox.addActionListener(e -> setFilter());

        returnonEquityComboBox.addItem(new FilterComboItem<>(NOT_SET, d -> true));
        returnonEquityComboBox.addItem(new FilterComboItem<>(">100%",d-> d > 100.0));
        returnonEquityComboBox.addItem(new FilterComboItem<>(">50%",d-> d > 50.0));
        returnonEquityComboBox.addItem(new FilterComboItem<>("20% < x < 50%",d-> d > 20.0 && d < 50.0));
        returnonEquityComboBox.addItem(new FilterComboItem<>("0% < x < 20%",d-> d > 0 && d < 20.0));
        returnonEquityComboBox.addItem(new FilterComboItem<>("-20% < x < 0%",d-> d > -20.0 && d < 0));
        returnonEquityComboBox.addItem(new FilterComboItem<>("-50% < x < -20%",d-> d > -50.0 && d < -20.0));
        returnonEquityComboBox.addItem(new FilterComboItem<>("<-50%",d-> d < -50.0));
        returnonEquityComboBox.addActionListener(e -> setFilter());

        epsGrowthComboBox.addItem(new FilterComboItem<>(NOT_SET, d -> true));
        epsGrowthComboBox.addItem(new FilterComboItem<>(">100%",d-> d > 100.0));
        epsGrowthComboBox.addItem(new FilterComboItem<>(">50%",d-> d > 50.0));
        epsGrowthComboBox.addItem(new FilterComboItem<>("20% < x < 50%",d-> d > 20.0 && d < 50.0));
        epsGrowthComboBox.addItem(new FilterComboItem<>("0% < x < 20%",d-> d > 0 && d < 20.0));
        epsGrowthComboBox.addItem(new FilterComboItem<>("-20% < x < 0%",d-> d > -20.0 && d < 0));
        epsGrowthComboBox.addItem(new FilterComboItem<>("-50% < x < -20%",d-> d > -50.0 && d < -20.0));
        epsGrowthComboBox.addItem(new FilterComboItem<>("<-50%",d-> d < -50.0));
        epsGrowthComboBox.addActionListener(e -> setFilter());

        dividendYieldComboBox.addItem(new FilterComboItem<>(NOT_SET, d -> true));
        dividendYieldComboBox.addItem(new FilterComboItem<>("Yes", d -> d > 0));
        dividendYieldComboBox.addItem(new FilterComboItem<>("No", d -> d == 0));
        dividendYieldComboBox.addActionListener(e -> setFilter());





        Set<String> industrySet = new HashSet<>();
        for(Object[] record: stocksData) {
            industrySet.add((String) record[2]);
        }
        List<String> industryList = new ArrayList<>(industrySet);
        Collections.sort(industryList);
        industrySelector.addItem(ALL_INDUSTRIES);
        for(Object industry: industryList) {
            industrySelector.addItem(industry);
        }

        DefaultTableModel model = new DefaultTableModel(stocksData, StockFileReader.getHeaderArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        stocksTable.setModel(model);
        sorter = new TableRowSorter<>(stocksTable.getModel());
        sorter.setComparator(3, Comparator.naturalOrder());
        sorter.setComparator(4, Comparator.naturalOrder());
        sorter.setComparator(5, Comparator.naturalOrder());
        sorter.setComparator(6, Comparator.naturalOrder());
        sorter.setComparator(7, Comparator.naturalOrder());
        sorter.setComparator(8, Comparator.naturalOrder());
        sorter.setComparator(9, Comparator.naturalOrder());

        stocksTable.setRowSorter(sorter);
        setFilter();
        industrySelector.addActionListener(e -> setFilter());
        companyNameFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setFilter();
            }
        });

        stocksTable.getColumnModel().getColumn(9).setCellRenderer(new PercentRenderer());
        stocksTable.getColumnModel().getColumn(8).setCellRenderer(new PercentRenderer());
        stocksTable.getColumnModel().getColumn(7).setCellRenderer(new PercentRenderer());
        stocksTable.getColumnModel().getColumn(6).setCellRenderer(new PercentRenderer());
        stocksTable.getColumnModel().getColumn(5).setCellRenderer(new RatioRenderer());
        stocksTable.getColumnModel().getColumn(3).setCellRenderer(new CapRenderer());

        stocksTable.getSelectionModel().addListSelectionListener(e -> {
            Number number = 0;
            if(stocksTable.getSelectedRow() != -1) {
                boolean isPeRatio = (float)stocksTable.getValueAt(stocksTable.getSelectedRow(), 5) < 25.0;
                boolean isRevenueGr = (float)stocksTable.getValueAt(stocksTable.getSelectedRow(), 6) > 0.1;
                float returnOnEq = (float)stocksTable.getValueAt(stocksTable.getSelectedRow(), 7);
                boolean isReturnOnEq =  15.0 <= returnOnEq && returnOnEq <= 20.0;
                boolean isEpsGr = (float)stocksTable.getValueAt(stocksTable.getSelectedRow(), 8) > 25.0;

                int total = 0;
                if(isPeRatio) total++;
                if(isRevenueGr) total++;
                if(isReturnOnEq) total++;
                if(isEpsGr) total++;

                switch (total) {
                    case 0:
                    case 1: number = 15;
                        break;
                    case 2:
                    case 3: number = 50;
                        break;
                    case 4: number = 80;
                }
            }
            data.setValue(number);
        });

        mainStockListPanel.setPreferredSize(new Dimension(1500, 600));
    }

    private void setFilter() {
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                return isIndustry(entry) && isName(entry) && isMktCap(entry) && isPriorClose(entry) && isPeRatio(entry) && isRevenueGrowth(entry)
                        && isReturnOnEquity(entry) && isEpsGrowth(entry) && isDividendYield(entry);
            }
        });
    }

    private boolean isPriorClose(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
        FilterComboItem<Float> priorCloseComboItem = (FilterComboItem<Float>) priorCloseComboBox.getSelectedItem();
        return priorCloseComboItem.getCompareMethod().test((Float) entry.getValue(4));
    }

    private boolean isMktCap(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
        FilterComboItem<Double> mktCapComboItem = (FilterComboItem) mktCapComboBox.getSelectedItem();
        return mktCapComboItem.getCompareMethod().test((Double) entry.getValue(3));
    }

    private boolean isPeRatio(RowFilter.Entry<? extends TableModel, ? extends Integer> entry){
        FilterComboItem<Float> peRatioComboItem = (FilterComboItem<Float>) peRatioComboBox.getSelectedItem();
        return peRatioComboItem.getCompareMethod().test((Float) entry.getValue(5));
    }

    private boolean isRevenueGrowth(RowFilter.Entry<? extends TableModel, ? extends Integer> entry){
        FilterComboItem<Float> revenueGrowthComboItem = (FilterComboItem<Float>) revenueGrowthComboBox.getSelectedItem();
        return revenueGrowthComboItem.getCompareMethod().test((Float) entry.getValue(6));
    }


    private boolean isReturnOnEquity(RowFilter.Entry<? extends TableModel, ? extends Integer> entry){
        FilterComboItem<Float> returnonEquityComboItem = (FilterComboItem<Float>) returnonEquityComboBox.getSelectedItem();
        return returnonEquityComboItem.getCompareMethod().test((Float) entry.getValue(7));
    }


    private boolean isEpsGrowth(RowFilter.Entry<? extends TableModel, ? extends Integer> entry){
        FilterComboItem<Float> epsGrowthComboItem = (FilterComboItem<Float>) epsGrowthComboBox.getSelectedItem();
        return epsGrowthComboItem.getCompareMethod().test((Float) entry.getValue(8));
    }


    private boolean isDividendYield(RowFilter.Entry<? extends TableModel, ? extends Integer> entry){
        FilterComboItem<Float> dividendYieldComboItem = (FilterComboItem<Float>) dividendYieldComboBox.getSelectedItem();
        return dividendYieldComboItem.getCompareMethod().test((Float) entry.getValue(9));
    }



    private boolean isName(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
        String nameFilter = companyNameFilter.getText();
        if(nameFilter != null && !nameFilter.isBlank()){
            String companyName = (String) entry.getValue(1);
            return companyName.toUpperCase().contains(nameFilter.toUpperCase());
        } else {
            return true;
        }
    }

    private boolean isIndustry(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
        String selectedIndustry = (String) industrySelector.getSelectedItem();
        if(selectedIndustry != null && !ALL_INDUSTRIES.equals(selectedIndustry)) {
            String entryIndustry = (String) entry.getValue(2);
            return entryIndustry.equals(industrySelector.getSelectedItem());
        } else {
            return true;
        }
    }

    public JPanel getMainStockListPanel() {
        return mainStockListPanel;
    }

    private void createUIComponents() {
        dialPanel = new JPanel();
        dialPanel.setPreferredSize(new Dimension(350, 300));
        buildDial(dialPanel);
    }

    private void buildDial(JPanel panel) {
        data = new DefaultValueDataset(0);
        MeterPlot plot = new MeterPlot(data);

        plot.setTickSize(100);
        plot.setOutlineStroke(new BasicStroke(1));
        plot.setMeterAngle(180);
        plot.setDialShape(DialShape.CHORD);
        plot.setDialBackgroundPaint(null);
        plot.setTickLabelsVisible(false);
        plot.setOutlineVisible(true);
        plot.setNeedlePaint(Color.black);
        plot.setDrawBorder(true);
        plot.addInterval(new MeterInterval("Normal", new Range(0, 33), Color.red, plot.getOutlineStroke(), Color.red));
        plot.addInterval(new MeterInterval("Warning", new Range(33, 64), Color.yellow, plot.getOutlineStroke(), Color.yellow));
        plot.addInterval(new MeterInterval("Critical", new Range(64, 100), Color.green, plot.getOutlineStroke(), Color.green));
        plot.setValuePaint(Color.white);
        plot.setUnits("");

        JFreeChart chart = new JFreeChart("Return on Investment", plot);
        chart.setBackgroundPaint(Color.white);
        chart.setBorderPaint(Color.white);
        chart.removeLegend();

        panel.add(new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 200);
            }
        });

    }
}
