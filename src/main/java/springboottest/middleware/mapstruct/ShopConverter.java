package springboottest.middleware.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopConverter {
    ShopConverter INSTANCE = Mappers.getMapper(ShopConverter.class);

    /**
     * dto -> do
     * @param dto
     * @return
     */
    Shop dto2do(ShopDTO dto);

    /**
     * do -> vo
     * @param shop
     * @return
     */
    ShopVO do2vo(Shop shop);
}
