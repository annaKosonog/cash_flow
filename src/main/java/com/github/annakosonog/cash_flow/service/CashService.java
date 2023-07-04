package com.github.annakosonog.cash_flow.service;

import com.github.annakosonog.cash_flow.exception.InvalidDetailsException;
import com.github.annakosonog.cash_flow.mappers.CashMapper;
import com.github.annakosonog.cash_flow.model.Cash;
import com.github.annakosonog.cash_flow.model.CashDto;
import com.github.annakosonog.cash_flow.model.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CashService {

    private final CashMapper cashMapper;

    private List<Cash> tomorrow() {
        return List.of(Cash.builder()
                        .date(LocalDate.of(2023, 12, 25))
                        .shop(Shop.CLOTHES_SHOP)
                        .price(new BigDecimal("45.99")).build(),

                Cash.builder()
                        .date(LocalDate.of(2023, 12, 21))
                        .shop(Shop.BOOKSHOP)
                        .price(new BigDecimal("32.5")).build(),

                Cash.builder()
                        .date(LocalDate.of(2024, 1, 21))
                        .shop(Shop.BOOKSHOP)
                        .price(new BigDecimal("23.5")).build()
        );
    }

    public List<CashDto> getAllCashFlow() {
        return  tomorrow()
                .stream()
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    public void getAddNewCash(CashDto newCash) {
        if (!isValid(newCash)) {
            throw new InvalidDetailsException("Invalid cash data");
        }

        final CashDto build = CashDto.builder()
                .date(newCash.getDate())
                .shop(newCash.getShop())
                .price(newCash.getPrice())
                .build();
        System.out.println("Added new cash_flow" + cashMapper.cashDtoToCash(build));
    }


    public List<CashDto> getCashByShop(String shop) {
        return tomorrow()
                .stream()
                .filter(searchShop -> searchShop.getShop().getCategoryStore().equals(shop))
                .map(cashMapper::cashToCashDto)
                .collect(Collectors.toList());
    }

    private boolean isValid(CashDto newCash) {
        return (newCash.getShop() != null && newCash.getDate() != null && newCash.getPrice() != null);
    }
}

