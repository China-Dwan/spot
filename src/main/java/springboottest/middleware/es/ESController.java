package springboottest.middleware.es;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboottest.middleware.es.pojo.Book;
import springboottest.middleware.es.service.BookRepository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/guest/es")
public class ESController {
    @Resource
    private BookRepository bookRepository;
    @Resource
    private TransportClient client;

    @RequestMapping("/add")
    public void add() {
        Book book = new Book();
        book.setId("1");
        book.setAuthor("1");
        book.setName("11");

        Book save = bookRepository.save(book);
        System.out.println(save);
    }

    /**
     * 查询,支持多条件
     */
    @RequestMapping("/get")
    public void get() {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("id","1"));
//                .must(QueryBuilders.termQuery("name", "11"));
//                .must(QueryBuilders.termQuery("author", "10"));
        Iterable<Book> search = bookRepository.search(query);
        Iterator<Book> iterator = search.iterator();
        while (iterator.hasNext()) {
            Book temp = iterator.next();
            System.out.println(temp);
        }
    }

    @RequestMapping("/page")
    public void page() {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("name", "11"))
                .must(QueryBuilders.termQuery("author", "10"));
        Iterable<Book> search = bookRepository.search(query, PageRequest.of(1,2, Sort.by(Sort.Direction.DESC,"id")));
        Iterator<Book> iterator = search.iterator();
        while (iterator.hasNext()) {
            Book temp = iterator.next();
            System.out.println(temp);
        }
    }

    @RequestMapping("/update")
    public void update() {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index("data");
            updateRequest.type("table");
            updateRequest.id("1");
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", "22");
            map.put("author", "22");
            updateRequest.doc(map);
            UpdateResponse response = client.update(updateRequest).get();
            System.out.println(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/delete")
    public void delete() {
        bookRepository.deleteById("1");
    }
}
