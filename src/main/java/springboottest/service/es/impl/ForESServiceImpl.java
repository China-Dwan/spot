package springboottest.service.es.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;
import springboottest.esrepository.ShopAdminRepository;
import springboottest.mapper.es.ShopAdminMapper;
import springboottest.pojo.es.ShopAdmin;
import springboottest.service.es.ForESService;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service
public class ForESServiceImpl implements ForESService {

    @Resource
    private ShopAdminMapper shopAdminMapper;
    @Resource
    private ShopAdminRepository shopAdminRepository;

    @Override
    public void in() {
        List<ShopAdmin> adminList = shopAdminMapper.selectList(Wrappers.lambdaQuery());
        shopAdminRepository.saveAll(adminList);
    }

    @Override
    public void select() {
        BoolQueryBuilder must = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("adminName", "dzh"));
        Iterable<ShopAdmin> itr = shopAdminRepository.search(must);
        Iterator<ShopAdmin> iterator = itr.iterator();
        if (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Override
    public void delete() {
        shopAdminRepository.deleteAll();
    }


}
