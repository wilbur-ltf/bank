package com.bank.common.exception;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 全局通用错误码
 */
@Getter
@ToString
@AllArgsConstructor
public enum CommonResponseCode implements IResponseCode {

    SUCCESS("200", "操作成功",HttpStatus.HTTP_OK),
    UNKNOWN("7777", "未知业务异常", HttpStatus.HTTP_INTERNAL_ERROR),
    INVALID_PARAM("7778", "参数校验失败", HttpStatus.HTTP_BAD_REQUEST),
    ;

    private final String code;

    private final String message;

    private final Integer httpStatus;

}
