package com.github.annakosonog.cash_flow.service;

import com.github.annakosonog.cash_flow.model.Cash;
import com.github.annakosonog.cash_flow.model.Shop;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashService {

    private List<Cash> tomorow() {
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
        return tomorow();
    }

    public void getAddNewCash(Cash newCash) {
        System.out.println("Added a new cash" + newCash);
    }

    public List<Cash> getCashByShop(String shop) {
        return tomorow()
                .stream()
                .filter(searchShop -> searchShop.getShop().getCategoryStore().equals(shop))
                .collect(Collectors.toList());
    }
}
