package com.github.annakosonog.cash_flow.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SimpleCashDao {

    default Cash firstCashFlowDao() {
        return Cash.builder()
                .id(1L)
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("42.80"))
                .build();
    }

    default Cash secondCashFlowDao() {
        return Cash.builder()
                .id(2L)
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.HAIRDRESSER)
                .price(new BigDecimal("20.0"))
                .build();
    }

    default CashFlow newExpenseDao() {
        return CashFlow.builder()
                .id(3L)
                .date(LocalDate.now().plusDays(1))
                .shop(Shop.GAS_STATION)
                .price(new BigDecimal("50.0"))
                .build();
    }


    default CashFlow newExpenseDaoNull() {
        return CashFlow.builder()
                .date(null)
                .shop(null)
                .price(null)
                .build();
    }

    default CashFlow secondSupermarketDao() {
        return CashFlow.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("100.80"))
                .build();
    }

    default CashFlow csvFirstDaoAfterSaved() {
        return CashFlow.builder()
                .id(1L)
                .date(LocalDate.of(2023, 7, 18))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("30.0"))
                .build();
    }
    default CashFlow csvFirstDaoBeforeSaving() {
        return CashFlow.builder()
                .id(1L)
                .date(LocalDate.of(2023, 7, 18))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("30.0"))
                .build();
    }

    default CashFlow csvSecondDaoAfterSaved() {
        return CashFlow.builder()
                .id(2L)
                .date(LocalDate.of(2023, 7, 18))
                .shop(Shop.GAS_STATION)
                .price(new BigDecimal("250.0"))
                .build();
    }

    default CashFlow csvSecondDaoBeforeSaving() {
        return CashFlow.builder()
                .id(null)
                .date(LocalDate.of(2023, 7, 18))
                .shop(Shop.GAS_STATION)
                .price(new BigDecimal("250.0"))
                .build();
    }
}
