package com.github.annakosonog.cash_flow.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface SimpleCashDao {
    default CashFlow firstCashFlowDao() {
        return CashFlow.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("42.80"))
                .build();
    }

    default CashFlow secondCashFlowDao() {
        return CashFlow.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.HAIRDRESSER)
                .price(new BigDecimal("20.0"))
                .build();
    }

    default CashFlow newExpenseDao() {
        return CashFlow.builder()
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
}
