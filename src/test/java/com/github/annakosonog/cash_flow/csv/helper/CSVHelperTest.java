package com.github.annakosonog.cash_flow.csv.helper;

import com.github.annakosonog.cash_flow.model.CashFlowDto;
import com.github.annakosonog.cash_flow.model.Shop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.github.annakosonog.cash_flow.csv.helper.CSVHelper.toReadTheCSVFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CSVHelperTest {

    @Test
    void toReadTheCSVFile_DataCorrect_ReadFile() {
        String csvData = "Date,Shop,Price" +
                "\n18.07.2023;SUPERMARKET;30.0" +
                "\n18.07.2023;GAS_STATION;250.0" +
                "\n19.07.2023;BOOKSHOP;18.11";


        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        List<CashFlowDto> result = toReadTheCSVFile(inputStream);

        assertEquals(3, result.size());
        assertEquals(LocalDate.of(2023, 7, 18), result.get(0).getDate());
        assertEquals(Shop.GAS_STATION, result.get(1).getShop());
        assertEquals(new BigDecimal("18.11"), result.get(2).getPrice());
    }

    @Test
    void toReadTheCSVFile_DataIncorrect_NoReadFile() {
        String csvData = "Date,Shop,Price" +
                "\n18.07.2023;SUPERMARKET;30.0" +
                "\n18.07.2023;GAS;250.0" +
                "\n19.07.2023;BOOKSHOP;18.11";

        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        assertThrows(RuntimeException.class, () -> toReadTheCSVFile(inputStream));
    }
}
