package com.example.demo.service.impl;

import com.example.demo.dao.HelloRepository;
import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    private HelloRepository helloRepository;

    @Async
    public Long getNum() {
        return helloRepository.count();
    }
}
