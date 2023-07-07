package com.github.annakosonog.cash_flow.mappers;

import com.github.annakosonog.cash_flow.model.Cash;
import com.github.annakosonog.cash_flow.model.CashDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CashMapper {

    CashMapper INSTANCE = Mappers.getMapper(CashMapper.class);


    CashDto cashToCashDto(Cash cash);

    Cash cashDtoToCash(CashDto cashDto);
}
