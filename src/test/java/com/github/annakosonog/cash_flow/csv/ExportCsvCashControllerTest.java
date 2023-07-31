package com.github.annakosonog.cash_flow.csv;

import com.github.annakosonog.cash_flow.model.SimpleCashDto;
import com.github.annakosonog.cash_flow.service.CashService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExportCsvCashControllerTest implements SimpleCashDto {

    private static final String EXPORT_CSV_PATH = "/api/csv/download";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CashService cashService;

    @BeforeEach
    void setupUp() {
        cashService.addNewCashFlow(firstCashFlowDto());
        cashService.addNewCashFlow(secondCashFlowDto());
    }

    @Test
    void downloadFile_DataCorrect_ShouldReturnHttpStatusOk() throws Exception {
        File file = File.createTempFile("example", ".csv");

        mockMvc.perform(MockMvcRequestBuilders.get(EXPORT_CSV_PATH)
                .param("file", file.getAbsolutePath())
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
        file.delete();
    }

    @Test
    void downloadFile_DataIncorrect_ShouldReturnHttpStatusNotFound() throws Exception {
        File file = File.createTempFile("example", ".cs");
        file.delete();

        mockMvc.perform(MockMvcRequestBuilders.get(EXPORT_CSV_PATH)
                .param("file", file.getAbsolutePath())
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
        file.delete();
    }

    @Test
    void downloadFile_DataIncorrect_ShouldReturnHttpStatusBadRequest() throws Exception {
        File file = File.createTempFile("example", ".csv");
        cashService.deleteCashFlowById(1L);
        cashService.deleteCashFlowById(2L);

        mockMvc.perform(MockMvcRequestBuilders.get(EXPORT_CSV_PATH)
                .param("file", file.getAbsolutePath())
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
        file.delete();
    }
}
