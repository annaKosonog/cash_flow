package com.github.annakosonog.cash_flow.service;
import com.github.annakosonog.cash_flow.exception.InvalidDetailsException;
import com.github.annakosonog.cash_flow.mappers.CashMapper;
import com.github.annakosonog.cash_flow.model.Cash;
import com.github.annakosonog.cash_flow.model.CashDto;
import com.github.annakosonog.cash_flow.model.Shop;
import com.github.annakosonog.cash_flow.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CashService {

    private final CashMapper cashMapper;
    private final CashRepository cashRepository;

    public List<CashDto> getAllCashFlow() {
        return cashRepository.findAll()
                .stream()
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    public void addNewCashFlow(CashDto newCashDto) {
        if (!isValid(newCashDto)) {
            throw new InvalidDetailsException("Invalid cash data");
        }
        final Cash newCash = cashMapper.cashDtoToCash(newCashDto);
        cashRepository.save(newCash);

    }

    public List<CashDto> getCashByShop(Shop shop) {
        return cashRepository.findByShop(shop)
                .stream()
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    private boolean isValid(CashDto newCash) {
        return (newCash.getShop() != null && newCash.getDate() != null && newCash.getPrice() != null);
    }
}

