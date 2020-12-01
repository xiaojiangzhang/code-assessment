package com.tools;

import java.io.IOException;
import ConfigPara.TypeEntity;
import com.bean.CoderData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
//import org.apache.commons.beanutils.
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OpenCSVReadBeansEx {
    public static void main(String[] args) throws IOException {

        String fileName = TypeEntity.getCsvPath();
        Path myPath = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<CoderData> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(CoderData.class);

            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(CoderData.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CoderData> cars = csvToBean.parse();

            cars.forEach(System.out::println);
        }
    }
}
