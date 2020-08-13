package com.middleware.mapstruct.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 到了展示层，我们又把对象封装成VO来与前端进行交互
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopVO implements Serializable {
    private String name;
    private Integer age;
    private String birthdayStr;
    private LocalDateTime birthday;
    private String amountStr;
    private BigDecimal amount;
}
