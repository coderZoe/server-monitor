package com.coderzoe.config;

import com.coderzoe.model.common.CommonReturn;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常拦截
 *
 * @author coderZoe
 * @date 2023/8/13 22:00
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonReturn<?> handle(Exception exception) {
        return CommonReturn.fail(exception.getMessage());
    }
}
