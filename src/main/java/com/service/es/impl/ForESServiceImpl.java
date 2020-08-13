package com.service.es.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;
import com.config.ESRestClientConfig;
import com.mapper.es.ShopAdminMapper;
import com.middleware.es.ShopAdmin;
import com.service.es.ForESService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class ForESServiceImpl implements ForESService {

    @Resource
    private ShopAdminMapper shopAdminMapper;

    @Override
    public void in() {
        RestHighLevelClient client = ESRestClientConfig.getClient();

        List<ShopAdmin> adminList = shopAdminMapper.selectList(Wrappers.lambdaQuery());

        for (ShopAdmin admin : adminList) {
            /**
             * 索引名称
             */
            IndexRequest request = new IndexRequest("admin", "doc", admin.getAdminId());
            String jsonString = JSON.toJSONString(admin);
            request.source(jsonString, XContentType.JSON);
            try {
                IndexResponse response = client.index(request, RequestOptions.DEFAULT);
                if (response.getResult() == DocWriteResponse.Result.CREATED) {
                    System.out.println(1);
                } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println(2);
                }
            } catch (Exception e) {
                //失败处理
                System.out.println(e.getMessage());
            }
        }

        closeClient(client);
    }

    @Override
    public void select() {
        RestHighLevelClient client = ESRestClientConfig.getClient();

        GetRequest request = new GetRequest("admin", "doc", "e76c20d193f74b758f6414be2576a7e6");
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                long version = response.getVersion();
                String s = response.getSourceAsString();
                System.out.println(s);
                ShopAdmin admin = JSON.parseObject(s, ShopAdmin.class);
                System.out.println(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeClient(client);
    }

    @Override
    public void delete() {
    }


    @Override
    public void multSelect() {
        RestHighLevelClient client = ESRestClientConfig.getClient();


        closeClient(client);
    }


    private void closeClient(RestHighLevelClient client) {
        try {
            client.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
