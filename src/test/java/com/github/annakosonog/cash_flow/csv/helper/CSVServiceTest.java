package com.github.annakosonog.cash_flow.csv.helper;

import com.github.annakosonog.cash_flow.mappers.CashMapper;
import com.github.annakosonog.cash_flow.model.CashFlow;
import com.github.annakosonog.cash_flow.model.SimpleCashDao;
import com.github.annakosonog.cash_flow.model.SimpleCashDto;
import com.github.annakosonog.cash_flow.repository.CashRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Example;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.github.annakosonog.cash_flow.csv.helper.CSVHelper.toReadTheCSVFile;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CSVServiceTest implements SimpleCashDao, SimpleCashDto {

    @Mock
    CashRepository cashRepository;

    @Mock
    CashMapper cashMapper;

    @InjectMocks
    CSVService csvService;

    @Test
    void saveTheCSVFileDateToTheDatabase_DataCorrect_toSaved() {
        String csvData = "Date,Shop,Price" +
                "\n18.07.2023;SUPERMARKET;30.0" +
                "\n18.07.2023;GAS_STATION;250.0";

        MockMultipartFile file = new MockMultipartFile("file.csv", csvData.getBytes());
        when(cashMapper.cashDtoToCash(csvFirstDto())).thenReturn(csvFirstDaoAfterSaved());
        when(cashMapper.cashDtoToCash(csvSecondDto())).thenReturn(csvSecondDaoAfterSaved());

        when(cashRepository.exists(Example.of(csvFirstDaoAfterSaved()))).thenReturn(false);
        when(cashRepository.exists(Example.of(csvSecondDaoAfterSaved()))).thenReturn(false);

        dateReadFromTheFile(csvData);
        csvService.saveTheCSVFileDateToTheDatabase(file);

        verify(cashRepository, times(2)).save(any(CashFlow.class));
    }

    @Test
    void saveTheCSVFileDateToTheDatabase_DataIncorrect_toSaveOneObjectWithList() {
        String csvData = "Date,Shop,Price" +
                "\n18.07.2023;SUPERMARKET;30.0" +
                "\n18.07.2023;GAS_STATION;250.0";

        when(cashRepository.save(csvFirstDaoBeforeSaving())).thenReturn(csvFirstDaoAfterSaved());

        MockMultipartFile file = new MockMultipartFile("file.csv", csvData.getBytes());

        when(cashMapper.cashDtoToCash(csvFirstDto())).thenReturn(csvFirstDaoAfterSaved());
        when(cashMapper.cashDtoToCash(csvSecondDto())).thenReturn(csvSecondDaoAfterSaved());

        when(cashRepository.exists(Example.of(csvFirstDaoAfterSaved()))).thenReturn(true);
        when(cashRepository.exists(Example.of(csvSecondDaoAfterSaved()))).thenReturn(false);

        dateReadFromTheFile(csvData);
        csvService.saveTheCSVFileDateToTheDatabase(file);

        verify(cashRepository, times(1)).save(any(CashFlow.class));
    }

    @Test
    void saveTheCSVFileDateToTheDatabase_DataIncorrect_WillNotSave() {
        String csvData = "Date,Shop,Price" +
                "\n18.07.2023;SUPERMARKET;30.0" +
                "\n18.07.2023;GAS_STATION;250.0";

        when(cashRepository.save(csvFirstDaoBeforeSaving())).thenReturn(csvFirstDaoAfterSaved());
        when(cashRepository.save(csvSecondDaoBeforeSaving())).thenReturn(csvSecondDaoAfterSaved());

        MockMultipartFile file = new MockMultipartFile("file.csv", csvData.getBytes());

        when(cashMapper.cashDtoToCash(csvFirstDto())).thenReturn(csvFirstDaoAfterSaved());
        when(cashMapper.cashDtoToCash(csvSecondDto())).thenReturn(csvSecondDaoAfterSaved());

        when(cashRepository.exists(Example.of(csvFirstDaoAfterSaved()))).thenReturn(true);
        when(cashRepository.exists(Example.of(csvSecondDaoAfterSaved()))).thenReturn(true);

        dateReadFromTheFile(csvData);
        csvService.saveTheCSVFileDateToTheDatabase(file);

        verify(cashRepository, never()).save(any(CashFlow.class));
    }

    @Test
    void saveTheCSVFileDateToTheDatabase_DataIncorrect_ThrowsRuntimeException(){
        String csvData = "Date,Shop,Price" +
                "\n18.07.2023;sUPERMARKET;30.0" +
                "\n18.07.2023;GAS_STATION;250.0";
        MockMultipartFile file = new MockMultipartFile("file.csv", csvData.getBytes());
        dateReadFromTheFile(csvData);
        assertThrows(RuntimeException.class, ()-> csvService.saveTheCSVFileDateToTheDatabase(file));
    }

    private void dateReadFromTheFile(String csvData) {
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        toReadTheCSVFile(inputStream);
    }


}
