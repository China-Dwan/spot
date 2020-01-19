package springboottest.middleware.es;

import org.elasticsearch.index.query.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboottest.middleware.es.pojo.Book;
import springboottest.middleware.es.service.BookRepository;

import javax.annotation.Resource;
import java.util.Iterator;

@RestController
@RequestMapping("/guest/es")
public class ESController {
    @Resource
    private BookRepository bookRepository;

    @RequestMapping("/add")
    public void add() {
        Book book = new Book();
        book.setId("1");
        book.setAuthor("1");
        book.setName("11");

        Book save = bookRepository.save(book);
        System.out.println(save);
    }

    @RequestMapping("/get")
    public void get() {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("name", "11"))
                .must(QueryBuilders.termQuery("author", "10"));
        Iterable<Book> search = bookRepository.search(query);
        Iterator<Book> iterator = search.iterator();
        while (iterator.hasNext()) {
            Book temp = iterator.next();
            System.out.println(temp);
        }
    }

    @RequestMapping("/page")
    public void page() {

    }
}
