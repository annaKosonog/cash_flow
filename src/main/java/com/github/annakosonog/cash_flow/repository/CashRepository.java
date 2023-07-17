package com.github.annakosonog.cash_flow.repository;
import com.github.annakosonog.cash_flow.model.Cash;
import com.github.annakosonog.cash_flow.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CashRepository extends JpaRepository<Cash, Long> {

    @Override
    List<Cash> findAll();

    List<Cash> findByShop(Shop shop);

}
