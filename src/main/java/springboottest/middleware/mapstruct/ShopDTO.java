package springboottest.middleware.mapstruct;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 在业务逻辑层，我们使用DTO来表示数据传输对象
 */
public class ShopDTO implements Serializable {
    private String name;
    private Integer age;
    private LocalDateTime birthday;
    private BigDecimal amount;
}
