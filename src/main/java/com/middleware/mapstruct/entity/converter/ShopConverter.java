package com.middleware.mapstruct.entity.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import com.middleware.mapstruct.dto.ShopDTO;
import com.middleware.mapstruct.entity.Shop;
import com.middleware.mapstruct.entity.vo.ShopVO;

@Mapper
public interface ShopConverter {
    ShopConverter INSTANCE = Mappers.getMapper(ShopConverter.class);

    /**
     * dto -> do
     * @param dto
     * @return
     */
    @Mappings({})
    Shop dto2do(ShopDTO dto);

    /**
     * do -> vo
     * @param shop
     * @return
     */
    @Mappings(value = {
            @Mapping(target = "birthdayStr", source = "birthday", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "birthday", source = "birthdayStr", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "amountStr", source = "amount", numberFormat = "0.00"),
            @Mapping(target = "amount", source = "amountStr", numberFormat = "0.00")
    })
    ShopVO do2vo(Shop shop);
}
