package springboottest.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface ExportMapper {
    List<Map<String,String>> exportBySQL();
}
