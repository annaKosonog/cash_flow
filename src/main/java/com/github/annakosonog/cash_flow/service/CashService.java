package com.github.annakosonog.cash_flow.service;
import com.github.annakosonog.cash_flow.exception.InvalidDetailsException;
import com.github.annakosonog.cash_flow.mappers.CashMapper;
import com.github.annakosonog.cash_flow.model.CashFlow;
import com.github.annakosonog.cash_flow.model.CashFlowDto;
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

    public List<CashFlowDto> getAllCashFlow() {
        return cashRepository.findAll()
                .stream()
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    public void addNewCashFlow(CashFlowDto newCashFlowDto) {
        if (!isValid(newCashFlowDto)) {
            throw new InvalidDetailsException("Invalid cash data");
        }
        final CashFlow newCashFlow = cashMapper.cashDtoToCash(newCashFlowDto);
        cashRepository.save(newCashFlow);
    }

    public List<CashFlowDto> getCashByShop(Shop shop) {
        return cashRepository.findByShop(shop)
                .stream()
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    private boolean isValid(CashFlowDto newCash) {
        return (newCash.getShop() != null && newCash.getDate() != null && newCash.getPrice() != null);
    }
}

