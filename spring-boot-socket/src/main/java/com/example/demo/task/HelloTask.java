package com.example.demo.task;

import com.example.demo.service.HelloService;
import com.example.demo.socket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloTask {
    @Autowired
    private HelloService helloService;
    @Autowired
    private WebSocket webSocket;
//    //表示方法执行完成后5秒
//    @Scheduled(fixedDelay=5000)
//    public void fixedDelayJob() throws InterruptedException{
//        System.out.println("fixedDelay 每隔5秒"+new Date());
//    }

    //表示每隔3秒
    @Scheduled(fixedRate=3000)
    public void fixedRateJob(){
        System.out.println("fixedRate 每隔3秒"+new Date());
        Double num = helloService.getNum()+ Math.random();
        webSocket.sendOneMessage("jack",num.toString());
    }

  /*  //表示每天8时30分0秒执行
    @Scheduled(cron="0 0,30 0,8 ? * ? ")
    public void cronJob(){ System.out.println(new Date()+" ...>>cron...."); }*/
}
