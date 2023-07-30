package com.github.annakosonog.cash_flow.csv;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/csv")
public class ExcelCsvCashController {

    private final CsvView csvView;

    @GetMapping("/cash")
    public void exportToExcel(HttpServletResponse response, @RequestParam("file") File file) throws IOException {
        csvView.prepareResponse(response, file);
        csvView.buildCsvDocumentProject(response);
    }
}
