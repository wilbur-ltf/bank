package com.bank.trade.domain.service;

import cn.hutool.core.util.ObjectUtil;
import com.bank.common.exception.UtilException;
import com.bank.trade.domain.model.TradeModel;
import com.bank.trade.domain.repository.ITradeRepository;
import jakarta.annotation.Resource;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.bank.trade.domain.exception.TradeResponseCode.DUPLICATE_TRADE;

/**
 * 说明，该层需要处理事务和异常，但是本案例中没有用到数据库，就不做事务的处理了。
 */
@Service
public class TradeService {

    @Resource(name = "tradeRepositorySession")
    private ITradeRepository tradeRepository;

    public TradeModel createTrade(@NonNull TradeModel model) {
        if (ObjectUtil.isNotNull(tradeRepository.selectTradeByCode(model.getTradeCode()))) {
            throw new UtilException(DUPLICATE_TRADE);
        }
        model.setCreateTime(LocalDateTime.now());
        return tradeRepository.saveTrade(model);
    }

    public void removeTrade(@NonNull Long id) {
        tradeRepository.deleteTrade(id);
    }

    public TradeModel modifyTrade(@NonNull TradeModel model) {
        TradeModel oldModel = tradeRepository.selectTradeByCode(model.getTradeCode());
        if (ObjectUtil.isNotNull(oldModel) && !ObjectUtil.equals(oldModel.getId(), model.getId())) {
            throw new UtilException(DUPLICATE_TRADE);
        }
        return tradeRepository.updateTrade(model);
    }

}
