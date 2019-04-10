package com.example.exception;

import com.example.common.Result;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    //捕捉shiro异常
    @ExceptionHandler(ShiroException.class)
    public Result shiroException(ShiroException e){
        return Result.builder().fail().code(403).message("您没有权限访问！"+e.getMessage()).build();
    }

    //捕捉异常
    @ExceptionHandler(NormalException.class)
    public Result normalException(NormalException e){
        return Result.builder().fail().code(500).message(e.getMessage()).build();
    }
}
