package com.middleware.activemq.producer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.middleware.activemq.MQUtil;

import javax.annotation.Resource;

@Component
@EnableScheduling
@RequestMapping("/mq")
public class TestAllMQProducer {

    @Resource
    private MQUtil mqUtil;

//    @Scheduled(fixedDelay = 5000)
    public void testMQ() {
        mqUtil.sendTestMQ("测试MQ封装");
    }

    /**
     * 延时消息
     */
    @RequestMapping("/delay")
    public void delayMQ() {
        mqUtil.sendDelayMQ("延时消息",10L);
    }

}
