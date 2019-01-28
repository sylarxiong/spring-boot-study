package com.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world!";
    }

    @RequestMapping("/login")
    public String login() {
        return "welcome!";
    }

    @RequestMapping("/info")
    public String info() {
        return "admin!";
    }

}
