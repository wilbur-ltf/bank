package com.bank.trade.application.query;

import com.bank.trade.application.converter.TradeConverter;
import com.bank.trade.domain.model.TradeModel;
import com.bank.trade.domain.repository.ITradeRepository;
import com.bank.trade.domain.vo.TradeVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeQueryService {

    @Resource(name = "tradeRepositorySession")
    private ITradeRepository tradeRepository;

    public List<TradeVO> listAllTrades() {
        List<TradeModel> list = tradeRepository.selectAllTrade();
        return list.stream().map(TradeConverter.INSTANCE::modelToVO).toList();
    }
}
