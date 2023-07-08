package com.github.annakosonog.cash_flow.repository;
import com.github.annakosonog.cash_flow.model.Cash;
import com.github.annakosonog.cash_flow.model.Shop;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CashRepositoryImpl implements CashRepository {

    private List<Cash> tomorrow() {
        List<Cash> cash = new ArrayList<>();

        Cash first = Cash.builder()
                .date(LocalDate.of(2023, 12, 25))
                .shop(Shop.CLOTHES_SHOP)
                .price(new BigDecimal("45.99")).build();

        Cash second = Cash.builder()
                .date(LocalDate.of(2023, 12, 21))
                .shop(Shop.BOOKSHOP)
                .price(new BigDecimal("32.5")).build();


        Cash third = Cash.builder()
                .date(LocalDate.of(2024, 1, 21))
                .shop(Shop.BOOKSHOP)
                .price(new BigDecimal("23.5")).build();

        cash.add(first);
        cash.add(second);
        cash.add(third);
        return cash;
    }


    @Override
    public List<Cash> findAll() {
        return tomorrow();
    }

    @Override
    public List<Cash> findByShop(String shop) {
        return tomorrow().stream()
                .filter(searchShop -> searchShop.getShop().getCategoryStore().equals(shop))
                .collect(Collectors.toList());
    }

    @Override
    public void addNewCash(Cash cash) {
        tomorrow().add(cash);
    }
}
