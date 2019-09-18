package springboottest.middleware.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import springboottest.middleware.es.pojo.Book;

public interface EsRepository extends ElasticsearchRepository<Book,String> {
}
