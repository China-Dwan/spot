package com.middleware.mapstruct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 在数据存储层，我们使用DO来抽象一个业务实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements Serializable {
    private String name;
    private Integer age;
    private LocalDateTime birthday;
    private String birthdayStr;
    private BigDecimal amount;
    private String amountStr;
}
