package com.github.annakosonog.cash_flow.csv;

import com.github.annakosonog.cash_flow.csv.controller.CSVController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/csv")
public class ExportCsvCashController {

    private static Logger logger = LoggerFactory.getLogger(CSVController.class);
    private final CsvView csvView;

    @GetMapping(value = "/download")
    public ResponseEntity<Void> downloadFile(HttpServletResponse response, @RequestParam("file") File file) {
        String message = "";
        if (!file.exists()) {
            message = "File not found: ";
            logger.info(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            csvView.prepareResponse(response, file);
            csvView.prepareCsvData(response);
            if (file.length() ==0) {
                message = "The file has a content of zero : ";
                logger.info(message);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            message = "Download the file successfully: ";
            logger.info(message);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            message = "Error while downloading the file: ";
            logger.warn(message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
