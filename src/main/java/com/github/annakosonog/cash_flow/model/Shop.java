package com.github.annakosonog.cash_flow.model;

public enum Shop {
    BOOKSHOP("bookshop"),
    CHEMIST("chemist"),
    GAS_STATION("gas_station"),
    HAIRDRESSER("hairdresser"),
    SUPERMARKET("supermarket"),
    DRUGSTORE("drugstore"),
    CLOTHES_SHOP("clothes_shop"),
    SPORT_SHOP("sport_shop");

    private String categoryStore;

    Shop(String categoryStore) {
        this.categoryStore = categoryStore;
    }

    public String getCategoryStore() {
        return categoryStore;
    }
}
