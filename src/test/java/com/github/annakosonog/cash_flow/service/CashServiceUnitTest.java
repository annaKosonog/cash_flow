package com.github.annakosonog.cash_flow.service;
import com.github.annakosonog.cash_flow.exception.InvalidDetailsException;
import com.github.annakosonog.cash_flow.mappers.CashMapper;
import com.github.annakosonog.cash_flow.model.CashFlow;
import com.github.annakosonog.cash_flow.model.CashFlowDto;
import com.github.annakosonog.cash_flow.model.Shop;
import com.github.annakosonog.cash_flow.model.SimpleCashDao;
import com.github.annakosonog.cash_flow.model.SimpleCashDto;
import com.github.annakosonog.cash_flow.repository.CashRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CashServiceUnitTest implements SimpleCashDto, SimpleCashDao {

    @Mock
    private CashMapper cashMapper;

    @Mock
    private CashRepository cashRepository;

    @InjectMocks
    private CashService cashService;

    @Test
    void getAllCashFlow_DataCorrect_FindAllCashFlow() {
        when(cashRepository.findAll()).thenReturn(List.of(firstCashFlowDao(), secondCashFlowDao()));
        when(cashMapper.cashToCashDto(firstCashFlowDao())).thenReturn(firstCashFlowDto());
        when(cashMapper.cashToCashDto(secondCashFlowDao())).thenReturn(secondCashFlowDto());

        final List<CashFlowDto> actual = cashService.getAllCashFlow();

        assertEquals(actual.size(), 2);
        assertEquals("supermarket", actual.get(0).getShop().getCategoryStore());
        assertEquals("hairdresser", actual.get(1).getShop().getCategoryStore());
    }

    @Test
    void addNewCash_DataCorrect_AddNewCash() {
        when(cashRepository.save(newExpenseDao())).thenReturn(newExpenseDao());
        when(cashMapper.cashDtoToCash(newExpenseDto())).thenReturn(newExpenseDao());
        cashService.addNewCashFlow(newExpenseDto());
        verify(cashRepository, times(1)).save(any(CashFlow.class));
    }

    @Test
    void addNewCash_DataIncorrect_ThrowInvalidDetailsException() {
        when(cashMapper.cashDtoToCash(newExpenseDtoNull())).thenReturn(newExpenseDao());
        assertThrows(InvalidDetailsException.class, () -> cashService.addNewCashFlow(newExpenseDtoNull()));
    }

    @Test
    void getCashByShop_DataCorrect_FindByShop() {
        final Shop shop = Shop.SUPERMARKET;
        final List<CashFlow> expected = List.of(firstCashFlowDao(), secondSupermarketDao());
        when(cashRepository.findByShop(shop)).thenReturn(expected);
        when(cashMapper.cashToCashDto(firstCashFlowDao())).thenReturn(firstCashFlowDto());
        when(cashMapper.cashToCashDto(secondSupermarketDao())).thenReturn(secondSupermarketDto());
        final List<CashFlowDto> actual = cashService.getCashByShop(shop);
        assertEquals(actual.size(), 2);
        assertEquals(actual.get(1).getPrice(), secondSupermarketDto().getPrice());
        assertEquals(actual.get(0).getPrice(), firstCashFlowDao().getPrice());
    }
}
