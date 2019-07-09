package com.example.demo.controller;

import com.example.demo.socket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
    @Autowired
    private WebSocket webSocket;

    @GetMapping("/sayHello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/sendAllWebSocket")
    public String test() {
        String text="你们好！这是websocket群体发送！";
        webSocket.sendAllMessage(text);
        return text;
    }

    @GetMapping("/sendOneWebSocket/{userName}")
    public String sendOneWebSocket(@PathVariable("userName")  String userName) {
        String text=userName+" 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(userName,text);
        return text;
    }
}
