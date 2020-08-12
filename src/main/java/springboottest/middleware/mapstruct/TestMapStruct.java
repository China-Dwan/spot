package springboottest.middleware.mapstruct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestMapStruct {

    public static void main(String[] args) {
        Shop shop = new Shop("name", 1, LocalDateTime.now(), BigDecimal.ZERO);
        ShopVO shopVO = ShopConverter.INSTANCE.do2vo(shop);
        System.out.println(shopVO);
    }
}
