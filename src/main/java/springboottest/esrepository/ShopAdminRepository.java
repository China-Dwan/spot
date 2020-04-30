package springboottest.esrepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import springboottest.pojo.es.ShopAdmin;

public interface ShopAdminRepository extends ElasticsearchRepository<ShopAdmin, String> {
}
