package com.bank.trade.infrastructure.session;

import cn.hutool.core.util.ObjectUtil;
import com.bank.common.exception.UtilException;
import com.bank.trade.domain.repository.ITradeRepository;
import com.bank.trade.domain.model.TradeModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.bank.trade.domain.exception.TradeResponseCode.TRADE_NOT_FOUND;

@Repository
public class TradeRepositorySession implements ITradeRepository {

    // 存储所有交易记录
    private final Map<Long, TradeModel> tradeToIdMap = new ConcurrentHashMap<>();

    private final Map<String, TradeModel> tradeToCodeMap = new ConcurrentHashMap<>();

    // 用于生成自增ID
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public TradeModel selectTradeByCode(String tradeCode) {
        return tradeToCodeMap.get(tradeCode);
    }

    @Override
    public TradeModel saveTrade(TradeModel model) {
        if (ObjectUtil.isNull(model.getId())) {
            model.setId(idGenerator.getAndIncrement());
        }
        tradeToIdMap.put(model.getId(), model);
        tradeToCodeMap.put(model.getTradeCode(), model);
        return model;
    }

    @Override
    public void deleteTrade(Long id) {
        if (!tradeToIdMap.containsKey(id)) {
            throw new UtilException(TRADE_NOT_FOUND);
        }
        TradeModel model = tradeToIdMap.get(id);
        tradeToIdMap.remove(id);
        tradeToCodeMap.remove(model.getTradeCode());
    }

    @Override
    public TradeModel selectTradeById(Long id) {
        return tradeToIdMap.get(id);
    }

    @Override
    public TradeModel updateTrade(TradeModel model) {
        Long id = model.getId();
        TradeModel oldModel = tradeToIdMap.get(id);
        if (ObjectUtil.isNull(oldModel)) {
            throw new UtilException(TRADE_NOT_FOUND);
        }
        tradeToIdMap.put(id, model);
        tradeToCodeMap.put(model.getTradeCode(), model);
        return model;
    }

    @Override
    public List<TradeModel> selectAllTrade() {
        return tradeToIdMap.values().stream().toList();
    }
}
