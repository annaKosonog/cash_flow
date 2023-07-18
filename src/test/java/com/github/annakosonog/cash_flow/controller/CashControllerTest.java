package com.github.annakosonog.cash_flow.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.annakosonog.cash_flow.model.CashFlowDto;
import com.github.annakosonog.cash_flow.model.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CashControllerTest {

    private static final String CASH_PATH = "/cash";
    private static final String ROOT_PATH = "$";
    private static final String SHOP_SUFFIX = ".shop";
    private static final String EMAIL_PATH = ROOT_PATH + SHOP_SUFFIX;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CashController cashController;

    @BeforeEach
    void setup() {
        Optional.ofNullable(cashController.getAllCashFlow())
                .map(ResponseEntity::getBody)
                .filter(patientsDto -> patientsDto.size() > 0)
                .ifPresent(patientsDto -> patientsDto.forEach(this::removeCash));
    }

    @Test
    void getAllCashFlow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(CASH_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_PATH).isArray())
                .andExpect(jsonPath("$[0].price").value("45.99"))
                .andExpect(jsonPath("$[1].shop").value("BOOKSHOP"))
                .andExpect(jsonPath("$[2].price").value("23.5"));
    }

    @Test
    void addANewCashFlow() throws Exception {
        mockMvc.perform(post(CASH_PATH)
                .content(json(newExampleCashFlow()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_PATH).isString())
                .andExpect(jsonPath(ROOT_PATH).value("Cash_flow was added successfully"));
    }

    @Test
    void getCashByShop() throws Exception {
        final String shop = "/bookshop";
        mockMvc.perform(get(CASH_PATH + shop)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_PATH).isArray())
                .andExpect(jsonPath("$[0].date").value((LocalDate.of(2023, 12, 21)).toString()))
                .andExpect(jsonPath("$[0].price").value("32.5"))
                .andExpect(jsonPath("$[1].date").value((LocalDate.of(2024, 1, 21)).toString()))
                .andExpect(jsonPath("$[1].price").value("23.5"));
    }

    private String json(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    private void removeCash(CashFlowDto cashFlowDto) {
        Objects.requireNonNull(cashController.getAllCashFlow().getBody()).remove(cashFlowDto);
    }

    private CashFlowDto newExampleCashFlow() {
        return CashFlowDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.CHEMIST)
                .price(new BigDecimal("15.20"))
                .build();
    }
}
