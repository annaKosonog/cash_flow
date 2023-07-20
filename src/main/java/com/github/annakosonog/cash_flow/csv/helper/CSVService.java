package com.github.annakosonog.cash_flow.csv.helper;

import com.github.annakosonog.cash_flow.mappers.CashMapper;
import com.github.annakosonog.cash_flow.model.CashFlow;
import com.github.annakosonog.cash_flow.model.CashFlowDto;
import com.github.annakosonog.cash_flow.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CSVService {

    private final CashRepository cashRepository;
    private final CashMapper cashMapper;

    public void saveTheCSVFileDateToTheDatabase(MultipartFile file) {
        try {
            List<CashFlowDto> list = CSVHelper.toReadTheCSVFile(file.getInputStream());
            List<CashFlowDto> checkedList = checkingIfDateFromCsvFileIsNotAlreadyInDb(list);
            for (CashFlowDto dto : checkedList) {
                CashFlow cashFlow = CashMapper.INSTANCE.cashDtoToCash(dto);
                cashRepository.save(cashFlow);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to store CSV date: " + e.getMessage());
        }
    }

    private  List<CashFlowDto> checkingIfDateFromCsvFileIsNotAlreadyInDb(List<CashFlowDto> list) {
        List<CashFlowDto> existsInDb = list.stream()
                .map(cashMapper::cashDtoToCash)
                .filter(example1 -> cashRepository.exists(Example.of(example1)))
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());

        for (CashFlowDto dto : existsInDb) {
            list.remove(dto);
        }
        return list;
    }
}
