package com.cpms.virtualization.services;

import com.cpms.virtualization.models.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.List;

/**
 * Created by Rakib on 2/16/2018.
 */
public class CSVManager {

    private static final String fileName = "DT_Delay_Data.csv";
    private static final String [] headers = new String[] {"status", "readTime", "renderTime"};

    public CSVManager() {
    }

    CSVRecord readRecordByUUID(String fileName, String uuid) {
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                String match = record.get("uuid");
                if (uuid.equals(match)) {
                    return record;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Wrong record identifier passed");
    }

    void appendRecords(List<Record> records) throws IOException {
        FileWriter out;
        CSVPrinter printer;

        File f = new File(fileName);
        if(f.exists() && !f.isDirectory()) {
            // without headers
            out = new FileWriter(fileName, true);
            printer = CSVFormat.EXCEL.print(out);
        } else {
            out = new FileWriter(fileName, true);
            printer = CSVFormat.EXCEL.withHeader(headers).print(out);
        }
        for (Record record : records) {
            printer.printRecord(record.getStatus(), record.getReadingTime(), record.getRenderTime());
        }
        out.close();
        printer.close();
    }
}
