package org.acme;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {

        StockFileReader stockFileReader = new StockFileReader();
        Object[][] stocksData = stockFileReader.readStocks();
        JFrame frame = new JFrame("Hello world");
        StockList stockList = new StockList();
        JPanel panel = stockList.getMainStockListPanel();
        stockList.setData(stocksData);



        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}