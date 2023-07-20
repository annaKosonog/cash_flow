package com.github.annakosonog.cash_flow.csv.controller;


import com.github.annakosonog.cash_flow.csv.helper.CSVHelper;
import com.github.annakosonog.cash_flow.csv.helper.CSVService;
import com.github.annakosonog.cash_flow.csv.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/csv")
public class CSVController {

    private static Logger logger = LoggerFactory.getLogger(CSVController.class);
    private final CSVService service;


    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = " ";

        if (CSVHelper.hasCSVFormat(file)) {
            logger.info("Checked files");
            try {
                service.saveTheCSVFileDateToTheDatabase(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                logger.info("Successfully uploaded file");
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, file.getOriginalFilename()));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + " !";
                logger.info("Could not upload the file: ");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
            }
        }
        message = "Please upload a CSV file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
    }
}
