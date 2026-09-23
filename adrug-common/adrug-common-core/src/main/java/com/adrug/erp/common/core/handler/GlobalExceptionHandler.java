package com.adrug.erp.common.core.handler;

import com.adrug.erp.common.result.Result;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理切面。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("BusinessException异常", e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("Exception异常", e);
        return Result.fail(ResultCode.SERVER_ERROR);
    }
}
