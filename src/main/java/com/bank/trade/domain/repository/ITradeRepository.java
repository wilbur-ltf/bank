package com.bank.trade.domain.repository;

import com.bank.trade.domain.model.TradeModel;

import java.util.List;


public interface ITradeRepository {

    /**
     * 根据交易流水号查询交易记录
     *
     * @param tradeCode 交易流水号
     * @return 交易记录
     */
    TradeModel selectTradeByCode(String tradeCode);
    /**
     * 保存交易记录
     *
     * @param model 交易记录
     * @return 交易记录
     */
    TradeModel saveTrade(TradeModel model);

    /**
     * 删除交易记录
     *
     * @param id 交易记录ID
     */
    void deleteTrade(Long id);

    /**
     * 根据ID查询交易记录
     *
     * @param id 交易记录ID
     * @return 交易记录
     */
    TradeModel selectTradeById(Long id);

    /**
     * 修改交易记录
     *
     * @param model 交易记录
     * @return 交易记录
     */
    TradeModel updateTrade(TradeModel model);

    /**
     * 查询所有交易记录
     *
     * @return 交易记录列表
     */
    List<TradeModel> selectAllTrade();

}
