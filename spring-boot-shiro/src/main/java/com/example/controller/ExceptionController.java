package com.example.controller;

import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public String handle401() {
        return  "您没有权限访问！";
    }
    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }
}
