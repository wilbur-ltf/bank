package com.bank.trade.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class TradeUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 交易流水号
     */
    @NotBlank(message = "交易流水号不能为空")
    private String tradeCode;

    /**
     * 交易金额
     */
    @NotNull(message = "交易金额不能为空")
    private BigDecimal amount;

    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    private Integer type;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 创建时间
     */
    @NotNull(message = "交易状态不能为空")
    private LocalDateTime createTime;



}
