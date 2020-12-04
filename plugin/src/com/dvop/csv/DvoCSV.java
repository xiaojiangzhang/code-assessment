package com.dvop.csv;

import com.csvreader.CsvWriter;
import groovy.json.StringEscapeUtils;

import java.io.*;
import java.nio.charset.Charset;

public class DvoCSV {
    public static void main(String[] args) {
//        String path = System.getProperties().getProperty("user.home");
//        String filePath = "\\data.csv";
//        File csvPath = new File(path + filePath);
//        if (!csvPath.exists()) {
//            try {
//                csvPath.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        writeCSVLine("sdfjk" +
//                "fdsa\nfdsa, " +
//                "\n\nfd,saf,dsa,f,dsaf,d,sfdsaf", csvPath.getPath());

    }

    //单行追加CSV文件记录
    public static void writeCSVLine(String string, String fName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fName, true)); // 附加
            // 添加新的数据行
            bw.write(string);
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }

    //创建csv文件插入表头数据
    public static void writeCSV(String writePath, String[] header) {
        String filePath = writePath;
        try {
            CsvWriter csvWriter = new CsvWriter(writePath, ',', Charset.forName("UTF-8"));
            csvWriter.writeRecord(header, true);
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeContent(String fileName, String content) {
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
