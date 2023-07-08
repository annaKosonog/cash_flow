package com.github.annakosonog.cash_flow.repository;
import com.github.annakosonog.cash_flow.model.Cash;
import java.util.List;

public interface CashRepository {
    List<Cash> findAll();

    List<Cash> findByShop(String shop);

    void addNewCash(Cash cash);

}
