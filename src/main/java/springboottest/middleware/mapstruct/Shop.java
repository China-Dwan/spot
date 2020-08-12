package springboottest.middleware.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 在数据存储层，我们使用DO来抽象一个业务实体
 */
@Data
@AllArgsConstructor
public class Shop implements Serializable {
    private String name;
    private Integer age;
    private LocalDateTime birthday;
    private BigDecimal amount;
}
