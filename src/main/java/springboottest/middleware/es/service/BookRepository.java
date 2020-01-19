package springboottest.middleware.es.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import springboottest.middleware.es.pojo.Book;

public interface BookRepository extends ElasticsearchRepository<Book,String> {
}
