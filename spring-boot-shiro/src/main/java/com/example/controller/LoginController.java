package com.example.controller;

import com.example.exception.NormalException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestParam String username,@RequestParam String password){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "login";
    }

    @GetMapping("/hello")
    @RequiresRoles(value ={"admin"},logical = Logical.OR)
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello1")
    @RequiresRoles(value ={"user"},logical = Logical.OR)
    public String hello1(){
        return "hello1";
    }

    @GetMapping("/ex")
    public String ex(){
        throw new NormalException("测试异常处理");
    }
}
