package com.github.annakosonog.cash_flow.model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShopConverter implements Converter<String, Shop> {
    @Override
    public Shop convert(String source) {
        return Shop.valueOf(source.toUpperCase());
    }
}
