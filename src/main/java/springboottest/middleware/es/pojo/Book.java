package springboottest.middleware.es.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Document注解
 * indexName  相当于数据库
 * type  相当于数据库的表
 */
@Data
@ToString
@Document(indexName = "data" , type = "table")
public class Book {
    @Id
    private String id;

    private String name;
    private String author;
}
