package springboottest.middleware.es;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboottest.middleware.es.pojo.Book;

import javax.annotation.Resource;

@RestController
@RequestMapping("/guest/es")
public class ESController {
    @Resource
    private EsRepository esRepository;

    @RequestMapping("/add")
    public void add() {
        Book book = new Book();
        book.setId("1");
        book.setAuthor("1");
        book.setName("11");

        Book save = esRepository.save(book);
        System.out.println(save);
    }

    @RequestMapping("/get")
    public void get() {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        //
        builder.should(QueryBuilders.matchPhrasePrefixQuery("text","11"));

        //设置查询的含有关键字
        builder.should(new QueryStringQueryBuilder("id").field(""));

        //设置分页,第一页开始,一页显示10条
        PageRequest page = new PageRequest(0, 10);

    }
}
