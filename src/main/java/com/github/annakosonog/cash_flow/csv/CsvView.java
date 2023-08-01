package com.github.annakosonog.cash_flow.csv;

import com.github.annakosonog.cash_flow.model.CashFlowDto;
import com.github.annakosonog.cash_flow.service.CashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvView {

    private final CashService cashService;

    protected void prepareResponse(HttpServletResponse response, File fileName) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName.getName() + "_" + currentDateTime + ".csv";

        response.setHeader(headerKey, headerValue);
    }

    public  void writeCsvData(HttpServletResponse response) {
        try(Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8)) {
        writer.write("\uFEFF");
        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

            List<CashFlowDto> listCash = prepareCsvData();

            String[] csvHeader = {"Date", "Shop", "Price"};
            String[] nameMapping = {"date", "shop", "price"};
            csvWriter.writeHeader(csvHeader);

            for (CashFlowDto dto : listCash) {
                csvWriter.write(dto, nameMapping);
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new HttpMessageNotWritableException("Could not prepare CSV file: " + e.getMessage());
        }
    }

    protected List<CashFlowDto> prepareCsvData() {
        return cashService.getAllCashFlow();
    }
}
