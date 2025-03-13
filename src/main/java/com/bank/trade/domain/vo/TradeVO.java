package com.bank.trade.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class TradeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 交易流水号
     */
    private String tradeCode;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易类型
     */
    private Integer type;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;



}
