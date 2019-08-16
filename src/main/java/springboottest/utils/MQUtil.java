package springboottest.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Queue;
import java.time.LocalTime;

@Component
@EnableJms
@Slf4j
public class MQUtil {
    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private Queue testMQ;
    @Resource
    private Queue delayMQ;



    public void sendTestMQ(String msg) {
        jmsTemplate.convertAndSend(testMQ,msg);
    }
    public void sendDelayMQ(String msg,long delayTime) {
        ActiveMQTextMessage mqMsg = new ActiveMQTextMessage();

        try {
            mqMsg.setText(msg);
            mqMsg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delayTime*1000);
        } catch (JMSException e) {
            log.error("延时信息封装失败" + e.toString());
        }

        System.out.println("发送延时消息时间: " + LocalTime.now());
        jmsTemplate.convertAndSend(delayMQ,mqMsg);
        System.out.println("发送延时消息完成时间: " + LocalTime.now());
    }
}
