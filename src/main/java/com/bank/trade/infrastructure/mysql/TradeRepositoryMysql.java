package com.bank.trade.infrastructure.mysql;

import com.bank.trade.domain.repository.ITradeRepository;
import com.bank.trade.domain.model.TradeModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO 作业要求用缓存集合，所以mysql的就不再实现了
 */
@Repository
public class TradeRepositoryMysql implements ITradeRepository {

    @Override
    public TradeModel selectTradeByCode(String tradeCode) {
        return null;
    }

    @Override
    public TradeModel saveTrade(TradeModel model) {
        return null;
    }

    @Override
    public void deleteTrade(Long id) {

    }

    @Override
    public TradeModel selectTradeById(Long id) {
        return null;
    }

    @Override
    public TradeModel updateTrade(TradeModel model) {
        return null;
    }

    @Override
    public List<TradeModel> selectAllTrade() {
        return List.of();
    }
}
