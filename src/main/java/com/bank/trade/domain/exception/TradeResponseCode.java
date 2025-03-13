package com.bank.trade.domain.exception;

import cn.hutool.http.HttpStatus;
import com.bank.common.exception.IResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 交易业务错误码
 */
@Getter
@ToString
@AllArgsConstructor
public enum TradeResponseCode implements IResponseCode {


    DUPLICATE_TRADE("90001", "交易单已存在", HttpStatus.HTTP_CONFLICT),
    TRADE_NOT_FOUND("90002", "交易单不存在", HttpStatus.HTTP_NOT_FOUND),
    ;

    private final String code;

    private final String message;

    private final Integer httpStatus;

}
