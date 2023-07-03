package com.github.annakosonog.cash_flow.service;

import com.github.annakosonog.cash_flow.exception.InvalidDetailsException;
import com.github.annakosonog.cash_flow.model.Cash;
import com.github.annakosonog.cash_flow.model.Shop;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashService {

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

    public List<Cash> getAllCashFlow() {
        return tomorrow();
    }

    public void getAddNewCash(Cash newCash) {
        if (!isValid(newCash)) {
            throw new InvalidDetailsException("Invalid cash data");
        }

        Cash.builder()
                .date(newCash.getDate())
                .shop(newCash.getShop())
                .price(newCash.getPrice())
                .build();
        System.out.println("Added new cash_flow");
    }



    public List<Cash> getCashByShop(String shop) {
        return tomorrow()
                .stream()
                .filter(searchShop -> searchShop.getShop().getCategoryStore().equals(shop))
                .collect(Collectors.toList());
    }

    private boolean isValid(Cash newCash) {
        return (newCash.getShop() != null && newCash.getDate() != null && newCash.getPrice() != null);
    }
}

