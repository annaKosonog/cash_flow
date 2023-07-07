package com.github.annakosonog.cash_flow.service;

import com.github.annakosonog.cash_flow.exception.InvalidDetailsException;
import com.github.annakosonog.cash_flow.mappers.CashMapper;
import com.github.annakosonog.cash_flow.model.CashDto;
import com.github.annakosonog.cash_flow.repository.CashRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CashService {

    private final CashMapper cashMapper;
    private final CashRepositoryImpl cashRepositoryImp;


    public List<CashDto> getAllCashFlow() {
        return cashRepositoryImp.findAll()
                .stream()
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    public void addNewCash(CashDto newCash) {
        if (!isValid(newCash)) {
            throw new InvalidDetailsException("Invalid cash data");
        }
        cashRepositoryImp.addNewCash(cashMapper.cashDtoToCash(newCash));
        System.out.println("Added new cash_flow");
    }


    public List<CashDto> getCashByShop(String shop) {
        return cashRepositoryImp.findByShop(shop)
                .stream()
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    private boolean isValid(CashDto newCash) {
        return (newCash.getShop() != null && newCash.getDate() != null && newCash.getPrice() != null);
    }
}

