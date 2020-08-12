package springboottest.business.miaosha;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springboottest.utils.R;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author dzh
 */
@RestController
@RequestMapping(value = "/ms")
public class MiaoShaController {
    private static final String REDIS_KEY = "REDIS_KEY";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(3, 2, 1);
        System.out.println(l1.equals(l2));
    }

    /**
     * 动态获取秒杀地址
     */
    @GetMapping(value = "/getpath")
    public R getPath(@RequestParam(name = "goodsId") String goodsId) {
        // userId
        String userId = "userId";

        // goodsId
        goodsId = "goodsId";

        // 每人5秒内只能获取一次
        String s = redisTemplate.opsForValue().get(REDIS_KEY);

        // 获取属于个人的随机path
        String personPath = getPathId();

        // 保存至redis
        HashOperations<String, Object, Object> redis = redisTemplate.opsForHash();
        redis.put(REDIS_KEY, userId+goodsId, personPath);

        return R.ok(personPath);
    }

    @PostMapping(value = "/{pathId}/submitorder")
    public R submitOrder(@PathVariable(value = "pathId") String pathId, String goodsId) {
        // userId
        String userId = "userId";

        // goodsId
        goodsId = "goodsId";

        HashOperations<String, Object, Object> redis = redisTemplate.opsForHash();
        String redisPathId = Objects.toString(redis.get(REDIS_KEY, userId+goodsId), "");

        // 校验通过之后即可继续进入下单页面
        return R.ok();
    }

    /**
     * uuid + 两位随机数
     */
    private static String getPathId() {
        MD5 md5 = MD5.create();
        return md5.digestHex(getUUID() + RandomUtil.randomNumbers(2));
    }

    private static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }
}
