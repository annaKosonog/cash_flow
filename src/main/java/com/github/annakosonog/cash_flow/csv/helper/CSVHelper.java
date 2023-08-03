package com.github.annakosonog.cash_flow.csv.helper;

import com.github.annakosonog.cash_flow.model.CashFlowDto;
import com.github.annakosonog.cash_flow.model.Shop;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHelper {
    public static String TYPE = "text/csv";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    static String[] HEADERs = {"Date", "Shop", "Price"};

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<CashFlowDto> toReadTheCSVFile(InputStream is) {
        List<CashFlowDto> cashFlowDtoList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.newFormat(',')
                     .withDelimiter(';')
                     .withQuote('"')
                     .withRecordSeparator('\n')
                     .withSkipHeaderRecord(true)
                     .withIgnoreEmptyLines(true)
                     .withIgnoreHeaderCase(false)
                     .withHeader(HEADERs)
                     .withTrim()
             )) {

            Iterable<CSVRecord> iterable = csvParser.getRecords();

            for (CSVRecord csvRecord : iterable) {
                CashFlowDto cashFlowDto = CashFlowDto.builder()
                        .date(LocalDate.parse(csvRecord.get(0), dateFormatter))
                        .shop(Shop.valueOf(csvRecord.get(1).toUpperCase()))
                        .price(new BigDecimal(csvRecord.get(2).trim()))
                        .build();

                cashFlowDtoList.add(cashFlowDto);
            }
            return cashFlowDtoList;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
        }
    }
}
