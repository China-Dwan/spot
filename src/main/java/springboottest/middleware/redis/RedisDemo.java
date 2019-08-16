package springboottest.middleware.redis;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("/guest/redis")
public class RedisDemo {

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/demo")
    public void test() {
        this.testString();
        this.testHash();
        this.testList();
        this.testSet();
        this.testZSet();
    }

    private void testZSet() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        for (int i = 0; i < 10; i++) {
            zSetOperations.add("4",i,1);
        }
        System.out.println(zSetOperations.range("4", 0, -1));
        System.out.println(zSetOperations.range("4",5,9));
    }

    private void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        for (int i = 0; i < 10; i++) {
            setOperations.add("3",i);
        }
        Set members = setOperations.members("3");
        System.out.println(members.size());
        System.out.println(members);
    }

    // key value[]
    private void testList() {
        ListOperations listOperations = redisTemplate.opsForList();
        for (int i = 0; i < 10; i++) {
            listOperations.leftPush("3",i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(redisTemplate.opsForList().rightPop("3"));
        }
    }

    // key = [{key,value},{key,value}]
    private void testHash() {
        for (int i = 0; i < 10; i++) {
            HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.put("2",i,i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(redisTemplate.opsForHash().get("2", i));
        }
    }

    // {key,value}
    //
    private void testString() {
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForValue().set("2",i);
            System.out.println(redisTemplate.opsForValue().get("2"));
        }
    }
}
