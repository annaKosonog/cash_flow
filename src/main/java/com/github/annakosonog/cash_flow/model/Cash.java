package com.github.annakosonog.cash_flow.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Cash {

    private LocalDate date;
    private Shop shop;
    private BigDecimal price;
}
