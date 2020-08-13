package com.middleware.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.middleware.activemq.ActiveMQConstant;

import java.time.LocalTime;

@Component
public class TestAllMQConsumer {


    @JmsListener(destination = ActiveMQConstant.testMQ)
    public void testMQ(String msg) {
        System.out.println("接收到的消息: " + msg);
    }
    @JmsListener(destination = ActiveMQConstant.delayMQ)
    public void delayMQ(String msg) {
        System.out.println("消费者接收到延时消息: " + msg + ",时间为: " + LocalTime.now());
    }
}
