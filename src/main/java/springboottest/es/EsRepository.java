package springboottest.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import springboottest.es.pojo.Book;

public interface EsRepository extends ElasticsearchRepository<Book,String> {
}
