package com.github.annakosonog.cash_flow.repository;
import com.github.annakosonog.cash_flow.model.CashFlow;
import com.github.annakosonog.cash_flow.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashRepository extends JpaRepository<CashFlow, Long> {

    @Override
    List<CashFlow> findAll();

    List<CashFlow> findByShop(Shop shop);

}
