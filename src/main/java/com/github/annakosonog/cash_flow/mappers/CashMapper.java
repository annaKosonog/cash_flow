package com.github.annakosonog.cash_flow.mappers;
import com.github.annakosonog.cash_flow.model.CashFlow;
import com.github.annakosonog.cash_flow.model.CashFlowDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CashMapper {

    CashMapper INSTANCE = Mappers.getMapper(CashMapper.class);

    CashFlowDto cashToCashDto(CashFlow cashFlow);

    CashFlow cashDtoToCash(CashFlowDto cashFlowDto);
}
