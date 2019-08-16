package springboottest.middleware.activemq.consumer;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;

@Component
@EnableScheduling
public class MessageReceiver {
    @Resource
    RedisTemplate redisTemplate;

    static int index = 0;
    Destination destination = new ActiveMQQueue("test.queue");

    @Resource
    private JmsTemplate jmsTemplate;


    @Scheduled(fixedDelay = 5000)
    public void sendMessage() {
//        index++;
//        redisTemplate.opsForValue().set("a",index);
//        redisTemplate.opsForValue().append("a",String.valueOf(index));

//        Boolean flag = redisTemplate.opsForValue().setIfAbsent("DOUBLE_CHECK", 1, 20, TimeUnit.SECONDS);//防止连点
//        System.out.println("生产者" + flag);

//        index++;
//        redisTemplate.opsForList().leftPush("a",index);
//        jmsTemplate.convertAndSend(destination,"发送:" + index);

//        index++;
//        redisTemplate.opsForList().leftPush("a",index);
//        jmsTemplate.convertAndSend(destination,"发送:" + index);

//        redisTemplate.opsForHash().put("hashMap","a","a");
//        jmsTemplate.convertAndSend("test.queue","发送");

//        redisTemplate.opsForSet().add("b","aa");
//        jmsTemplate.convertAndSend(new ActiveMQQueue("test.queue"),"f");
    }

    @JmsListener(destination = "test.queue")
    public void receiveMessage(String text) {
//        System.out.println(text);

//        System.out.println(redisTemplate.opsForValue().get("a"));


//        System.out.println(redisTemplate.opsForList().rightPop("a"));

//        System.out.println(redisTemplate.opsForHash().get("hashMap", "a"));

//        System.out.println(redisTemplate.opsForSet().isMember("b", "aa"));
//        System.out.println(redisTemplate.opsForSet().isMember("b","bb"));

    }


}
