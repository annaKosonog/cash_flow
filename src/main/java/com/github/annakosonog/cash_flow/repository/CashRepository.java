package com.github.annakosonog.cash_flow.repository;

import com.github.annakosonog.cash_flow.model.Cash;

import java.util.List;
import java.util.Optional;

public interface CashRepository {
    List<Cash> findAll();

    Optional<Cash> findByShop(String shop);

    void addNewCash(Cash cash);

}
