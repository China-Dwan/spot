package springboottest.es.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@ToString
@Document(indexName = "data" , type = "table")
public class Book {
    @Id
    private String id;

    private String name;
    private String author;
}
