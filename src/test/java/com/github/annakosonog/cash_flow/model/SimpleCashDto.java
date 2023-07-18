package com.github.annakosonog.cash_flow.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface SimpleCashDto {
    default CashFlowDto firstCashFlowDto() {
        return CashFlowDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("42.80"))
                .build();
    }

    default CashFlowDto secondCashFlowDto() {
        return CashFlowDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.HAIRDRESSER)
                .price(new BigDecimal("20.0"))
                .build();
    }

    default CashFlowDto newExpenseDto() {
        return CashFlowDto.builder()
                .date(LocalDate.now().plusDays(1))
                .shop(Shop.GAS_STATION)
                .price(new BigDecimal("50.0"))
                .build();
    }

    default CashFlowDto newExpenseDtoNull() {
        return CashFlowDto.builder()
                .date(null)
                .shop(null)
                .price(null)
                .build();
    }

    default CashFlowDto secondSupermarketDto() {
        return CashFlowDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.SUPERMARKET)
                .price(new BigDecimal("100.80"))
                .build();
    }
}
