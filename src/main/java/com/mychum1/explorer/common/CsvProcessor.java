package com.mychum1.explorer.common;

import com.opencsv.CSVWriter;


import java.io.FileWriter;
import java.io.IOException;

public class CsvProcessor {

    public static void test() throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter("./test.csv"));
        String[] fields = {"a","b","c"};
        csvWriter.writeNext(new String[]{"d","e","f"});
        csvWriter.close();
    }
}
