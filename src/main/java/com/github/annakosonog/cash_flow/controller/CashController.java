package com.github.annakosonog.cash_flow.controller;

import com.github.annakosonog.cash_flow.model.CashFlowDto;
import com.github.annakosonog.cash_flow.model.Shop;
import com.github.annakosonog.cash_flow.service.CashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/cash")
@RequiredArgsConstructor
@RestController
public class CashController {

    private final CashService cashService;

    @GetMapping
    public ResponseEntity<List<CashFlowDto>> getAllCashFlow() {
        return ResponseEntity.ok(cashService.getAllCashFlow());
    }

    @GetMapping("/{shop}")
    public ResponseEntity<List<CashFlowDto>> getAllCashFlowWithGivenStore(@PathVariable Shop shop) {
        return ResponseEntity.ok(cashService.getCashByShop(shop));
    }

    @PostMapping
    public ResponseEntity<String> addNewCost(@RequestBody CashFlowDto newCash) {
        cashService.addNewCashFlow(newCash);
        return ResponseEntity.ok("Cash_flow was added successfully");
    }
}
