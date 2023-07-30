package com.github.annakosonog.cash_flow.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.annakosonog.cash_flow.model.CashDto;
import com.github.annakosonog.cash_flow.model.Shop;
import com.github.annakosonog.cash_flow.model.SimpleCashDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CashControllerTest implements SimpleCashDto {

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
        cashController.addNewCash(firstCashFlowDto());
        cashController.addNewCash(secondCashFlowDto());
    }

    @AfterEach
    void delete() {
        cashController.deleteCashFlow(1L);
        cashController.deleteCashFlow(2L);
    }

    @Test
    void getAllCashFlow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(CASH_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_PATH).isArray())
                .andExpect(jsonPath("$[0].price").value("42.8"))
                .andExpect(jsonPath("$[1].price").value("20.0"));
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
        cashController.addNewCash(secondSupermarketDto());
        final String shop = "/supermarket";
        mockMvc.perform(get(CASH_PATH + shop)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_PATH).isArray())
                .andExpect(jsonPath("$[0].price").value("42.8"))
                .andExpect(jsonPath("$[1].date").value((LocalDate.now().plusDays(2)).toString()))
                .andExpect(jsonPath("$[1].price").value("100.8"));
    }

    @Test
    void deleteCashFlow() throws Exception {
        cashController.addNewCash(newExpenseDto());

        mockMvc.perform(MockMvcRequestBuilders.delete(CASH_PATH + "/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_PATH).isString())
                .andExpect(jsonPath(ROOT_PATH).value("Cash_flow was deleted successfully"));
    }

    private String json(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }


    private CashDto newExampleCashFlow() {
        return CashDto.builder()
                .date(LocalDate.now().plusDays(2))
                .shop(Shop.CHEMIST)
                .price(new BigDecimal("15.20"))
                .build();
    }
}
