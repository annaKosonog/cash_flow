package com.github.annakosonog.cash_flow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class CashFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    @Enumerated(value = EnumType.STRING)
    private Shop shop;
    private BigDecimal price;

    @Override
    public String toString() {
        return "CashFlow " +
                "[id=" + id +
                ", date=" + date +
                ", shop=" + shop +
                ", price=" + price +
                ']';
    }
}
