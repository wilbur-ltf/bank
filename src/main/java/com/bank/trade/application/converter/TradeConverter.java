package com.bank.trade.application.converter;

import com.bank.trade.domain.dto.TradeCreateDTO;
import com.bank.trade.domain.dto.TradeUpdateDTO;
import com.bank.trade.domain.model.TradeModel;
import com.bank.trade.domain.vo.TradeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TradeConverter {

    TradeConverter INSTANCE = Mappers.getMapper(TradeConverter.class);

    TradeModel crateDTOToModel(TradeCreateDTO createDTO);

    TradeModel updateDTOToModel(TradeUpdateDTO updateDTO);

    TradeVO modelToVO(TradeModel tradeModel);

}
