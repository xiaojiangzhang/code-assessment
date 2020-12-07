package com.tools;

import java.io.IOException;

import ConfigPara.TypeEntity;
import com.bean.CodeIfo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//读取csv，并将列名映射为对象
public class OpenCSVReadBeansEx {
    public List<CodeIfo> readBeans(String startTime, String endTime, String path) {
//        String fileName = TypeEntity.getCsvPath();
        Path myPath = Paths.get(path);
        List<CodeIfo> listCodeInfo = null;
        List<CodeIfo> codeIfoList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {
            HeaderColumnNameMappingStrategy<CodeIfo> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(CodeIfo.class);
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(CodeIfo.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            listCodeInfo = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (CodeIfo codeIfo : listCodeInfo) {
            if (codeIfo.getTime().compareTo(startTime) >= 0 && endTime.compareTo(codeIfo.getTime()) >= 0) {
                codeIfoList.add(codeIfo);
            }
        }
        return codeIfoList;
    }

    public static void main(String[] args) throws IOException {
        OpenCSVReadBeansEx openCSVReadBeansEx = new OpenCSVReadBeansEx();
        List<CodeIfo> codeIfoList = openCSVReadBeansEx.readBeans("2020-11-29 17:40:20","2020-12-07 17:02:54", TypeEntity.getCsvPath());
        System.out.println(codeIfoList.size());
    }
}
