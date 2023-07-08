package com.github.annakosonog.cash_flow.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface SimpleCashDao {
    default Cash firstCashFlowDao() {
        return Cash.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("42.80"))
                .build();
    }

    default Cash secondCashFlowDao() {
        return Cash.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.HAIRDRESSER)
                .price(new BigDecimal("20.0"))
                .build();
    }

    default Cash newExpenseDao() {
        return Cash.builder()
                .date(LocalDate.now().plusDays(1))
                .shop(Shop.GAS_STATION)
                .price(new BigDecimal("50.0"))
                .build();
    }

    default Cash newExpenseDaoNull() {
        return Cash.builder()
                .date(null)
                .shop(null)
                .price(null)
                .build();
    }

    default Cash secondSupermarketDao() {
        return Cash.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("100.80"))
                .build();
    }
}
