package org.acme;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StockFileReader {

    public static String[] getHeaderArray() {
        return new String[]{"Symbol","Company Name","Industry","Mkt Cap","Prior Close","P/E Ratio (TTM)*",
                        "Revenue Growth CFY*","Return on Equity","EPS Growth CFY*","Dividend Yield"};
    }
    public Object[][] readStocks() throws Exception {
        File file = new File("StockList.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        CSVFormat format = CSVFormat.Builder.create()
                .setDelimiter(",")
                .setHeader()
                //.setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();
        CSVParser parser = new CSVParser(inputStreamReader, format);
        List<CSVRecord> recordList = parser.getRecords();
        Object[][] result = new Object[recordList.size()][10];
        int i = 0;
        for(CSVRecord csvRecord: recordList) {
            for(int k=0; k < 10; k++) {
                if(k==0 || k==1 || k==2) {
                    result[i][k] = csvRecord.get(k);
                } else if(k == 3) {
                    String cap = csvRecord.get(k);
                    double capFloat = new DecimalFormat("$000.000").parse(cap).doubleValue();
                    double capDouble = capFloat * 1000;
                    String end = cap.substring(cap.length()-1);
                    double capFinal;
                    if("M".equals(end)) {
                        capFinal = (double) (capDouble * 1000L);
                    } else if("B".equals(end)) {
                        capFinal = (double) (capDouble * 1000000L);
                    } else if("T".equals(end)) {
                        capFinal = (double) (capDouble * 1000000000L);
                    } else {
                        throw new Exception("Bad data: "+csvRecord.get(k));
                    }
                    //System.out.println("Reading original: "+cap+"       saving: "+capFinal);
                    result[i][k] = capFinal;
                } else if(k==4) {
                    String close = csvRecord.get(k);
                    result[i][k] = new DecimalFormat("0000.00").parse(close).floatValue();
                } else if(k==5) {
                    String ratio = csvRecord.get(k);
                    try {
                        result[i][k] = new DecimalFormat("00.00").parse(ratio).floatValue();
                    } catch (ParseException pe) {
                        result[i][k] = (float) 0;
                    }
                } else if(k==8 || k==7 || k==6) {
                    String dividend = csvRecord.get(k);
                    result[i][k] = new DecimalFormat("00.00").parse(dividend).floatValue();
                } else if(k==9) {
                    String dividend = csvRecord.get(k);
                    result[i][k] = new DecimalFormat("0.00").parse(dividend).floatValue();
                } else {
                    result[i][k] = csvRecord.get(k);
                }
            }
            i++;
        }
        return result;
    }


}
