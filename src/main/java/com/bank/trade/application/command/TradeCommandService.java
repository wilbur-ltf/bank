package com.bank.trade.application.command;

import com.bank.trade.application.converter.TradeConverter;
import com.bank.trade.domain.dto.TradeCreateDTO;
import com.bank.trade.domain.dto.TradeUpdateDTO;
import com.bank.trade.domain.model.TradeModel;
import com.bank.trade.domain.service.TradeService;
import com.bank.trade.domain.vo.TradeVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class TradeCommandService {

    @Autowired
    private TradeService tradeService;

    public TradeVO createTrade(@NonNull @Valid TradeCreateDTO dto) {
        // dto 转 model
        TradeModel model = TradeConverter.INSTANCE.crateDTOToModel(dto);
        // 持久化操作
        model = tradeService.createTrade(model);
        // model 转 vo 并返回
        return TradeConverter.INSTANCE.modelToVO(model);
    }

    public void removeTrade(@NotNull(message = "id不能为空") Long id) {
        tradeService.removeTrade(id);
    }

    public TradeVO modifyTrade(@NonNull @Valid TradeUpdateDTO dto) {
        // dto 转 model
        TradeModel model = TradeConverter.INSTANCE.updateDTOToModel(dto);
        // 持久化操作
        model = tradeService.modifyTrade(model);
        // model 转 vo 并返回
        return TradeConverter.INSTANCE.modelToVO(model);
    }

}
