package com.mychum1.explorer.common;

import com.mychum1.explorer.domain.CsvData;
import com.mychum1.explorer.repository.CsvDataRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvProcessor {

    @Autowired
    CsvDataRepository csvDataRepository;

    public void test() throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter("./test.csv"));
        String[] fields = {"a","b","c"};
        csvWriter.writeNext(new String[]{"d","e","f"});
        csvWriter.close();
    }

    public List<CsvData> readCsvData() {

        return csvDataRepository.findAll();
    }

    public void saveCsvData() throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new FileReader("./test.csv"));
        String [] nextLine;
        List<CsvData> dataList = new ArrayList<CsvData>();
        while ((nextLine = csvReader.readNext()) != null) {   // 2
            CsvData data = new CsvData();
            data.setA(nextLine[0]);
            data.setB(nextLine[1]);
            data.setC(nextLine[2]);
//            for (int i = 0; i < nextLine.length; i++) {
//                System.out.println(i + " " + nextLine[i]);
//            }
            dataList.add(data);
            System.out.println();
        }
        csvDataRepository.saveAll(dataList);

    }
}
