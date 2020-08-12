package springboottest.middleware.mapstruct;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 到了展示层，我们又把对象封装成VO来与前端进行交互
 */
public class ShopVO implements Serializable {
    private String name;
    private Integer age;
    private LocalDateTime birthday;
    private BigDecimal amount;
}
