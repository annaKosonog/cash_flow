package com.github.annakosonog.cash_flow.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface SimpleCashDto {
    default CashDto firstCashFlowDto() {
        return CashDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("42.80"))
                .build();
    }

    default CashDto secondCashFlowDto() {
        return CashDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.HAIRDRESSER)
                .price(new BigDecimal("20.0"))
                .build();
    }

    default CashDto newExpenseDto() {
        return CashDto.builder()
                .date(LocalDate.now().plusDays(1))
                .shop(Shop.GAS_STATION)
                .price(new BigDecimal("50.0"))
                .build();
    }

    default CashDto newExpenseDtoNull() {
        return CashDto.builder()
                .date(null)
                .shop(null)
                .price(null)
                .build();
    }

    default CashDto secondSupermarketDto() {
        return CashDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("100.80"))
                .build();
    }
}
