package com.middleware.mapstruct;

import com.middleware.mapstruct.entity.Shop;
import com.middleware.mapstruct.entity.converter.ShopConverter;
import com.middleware.mapstruct.entity.vo.ShopVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestMapStruct {

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.setName("name");
        shop.setAge(1);
        shop.setBirthday(LocalDateTime.now());
        shop.setBirthdayStr("1993-01-01 00:00:00");
        shop.setAmount(BigDecimal.ZERO);
        shop.setAmountStr("3.33");
        System.out.println(shop);
        ShopVO shopVO = ShopConverter.INSTANCE.do2vo(shop);
        System.out.println(shopVO);
    }
}
