package springboottest.cancelorder;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AutoCancelOrder {
    private final static String redisKey = "ORDER_AUTO_CANCEL_HASH";

    @Resource
    private RedisTemplate redisTemplate;

    //保存需要取消的订单
    private final static DelayQueue<OrderDelayedVO> delayedQueue = new DelayQueue<>();
    //是否开始标记
    private boolean ifStart;


    @PostConstruct
    public void startCancelTask() {
        //启动线程,执行取消订单业务
        this.start();

        //读取redis中的遗留业务
        Map<String,Object> cacheData = redisTemplate.opsForHash().entries(redisKey);
        cacheData.keySet().forEach(key -> {
            OrderDelayedVO order = (OrderDelayedVO) cacheData.get(key);
            if (order == null) {
                return;
            }

            //将订单信息加入Java队列
            this.add(order);
        });
    }


    /**
     * 订单保存进延时队列及缓存
     * @param order  需要处理的订单
     */
    private void add(OrderDelayedVO order) {
        try {
            delayedQueue.put(order);
            redisTemplate.opsForHash().put(redisKey,order.getOrderId(), JSON.toJSONString(order));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动线程开始执行取消订单逻辑
     */
    private synchronized void start() {
        if (ifStart) {
            //此时已启动
            return;
        }
        ifStart = true;

        //创建线程池
        ThreadPoolExecutor threadPool = ThreadPoolUtil.getThreadPool();
        new Thread(() -> {
            while (true) {
                try {
                    //获取延迟队列中待取消的订单
                    OrderDelayedVO order = delayedQueue.take();

                    //启动取消订单任务
                    threadPool.execute(() -> cancelOrder(order));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 订单取消操作
     * @param order
     */
    private void cancelOrder(OrderDelayedVO order) {
        //拼接redis锁
        String lockKey = String.format("%s%s", redisKey, order.getOrderId());

        try {
            if (redisTemplate.opsForValue().setIfAbsent(lockKey,order.getOrderId(),60l, TimeUnit.SECONDS)) {
                String orderJson = (String) redisTemplate.opsForHash().get(redisKey, order.getOrderId());
                if (StringUtils.isNotEmpty(orderJson)) {
                    //此订单已经执行过取消订单操作
                    return;
                }

                //更新订单状态
                updateOrder(order);

                //删除缓存中的订单信息
                redisTemplate.opsForHash().delete(redisKey,order.getOrderId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //是否需要补偿数据?

        } finally {
            //删除锁
            redisTemplate.delete(lockKey);
        }
    }

    /**
     * 更新订单状态
     * @param order
     */
    private void updateOrder(OrderDelayedVO order) {

    }
}
