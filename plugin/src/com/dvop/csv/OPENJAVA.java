package com.dvop.csv;

import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OPENJAVA {

    public static void main(String[] args) throws IOException {

        String[] items1 = {"bocx.,ok", "coin", "pencil"};
        String[] items2 = {"pen", "chair", "lamp"};
        String[] items3 = {"ball", "bowl", "spectacles"};

        List<String[]> entries = new ArrayList<>();
        entries.add(items1);
        entries.add(items2);
        entries.add(items3);

        String fileName = "items.csv";
//        FileWriter mFileWriter = new FileWriter(fileName, true);
//        CSVWriter mCsvWriter = new CSVWriter(mFileWriter);
//        mCsvWriter.writeAll(entries);
        CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));
        writer.writeNext(items1);
        writer.close();
    }
}
