package com.github.annakosonog.cash_flow.csv.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CSVControllerTest {

    private static final String CSV_PATH = "/api/csv";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CSVController csvController;

    @Test
    void uploadFile_DataCorrect_UploadAndSaveFileToDb() throws Exception {
        String content = "Date,Shop,Price" +
                "\n18.07.2023;SUPERMARKET;30.0" +
                "\n18.07.2023;GAS_STATION;250.0" +
                "\n19.07.2023;BOOKSHOP;18.11";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "cash.csv",
                "text/csv",
                content.getBytes());

        mockMvc
                .perform(MockMvcRequestBuilders.multipart(CSV_PATH + "/upload")
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Uploaded the file successfully: cash.csv"))
                .andExpect(jsonPath("$.fileDownloadUri").value("cash.csv"));
    }

    @Test
    void uploadFile_DataIncorrect_isBadRequest() throws Exception {
        String content = "Date,Shop,Price" +
                "\n18.07.2023;GAS;250.0";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "cash.txt",
                "text/txt",
                content.getBytes());

        mockMvc
                .perform(MockMvcRequestBuilders.multipart(CSV_PATH + "/upload")
                        .file(file))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Please upload a CSV file!"))
                .andExpect(jsonPath("$.fileDownloadUri").value(""));
    }

    @Test
    void uploadFile_DataIncorrect_isExpectationFailed() throws Exception {
        String content = "Date,Shop,Price" +
                "\n18.07.2023;SUPERMARKET;30.0" +
                "\n18.07.2023;GAS___STATION;250.0" +
                "\n19.07.2023;BOOKSHOP;18.11";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "cash.csv",
                "text/csv",
                content.getBytes());

        mockMvc
                .perform(MockMvcRequestBuilders.multipart(CSV_PATH + "/upload")
                        .file(file))
                .andDo(print())
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.message").value("Could not upload the file: cash.csv"));
    }

}
