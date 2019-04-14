package com.example.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一处理异常
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NormalException.class)
    public Result normalException(NormalException normalException){
        return Result.builder().fail().message(normalException.getMessage()).build();
    }
}
