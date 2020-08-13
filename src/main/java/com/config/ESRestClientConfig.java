package com.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ESRestClientConfig {

    public static RestHighLevelClient getClient() {
        //  如果有多个节点可以持续在内部new多个HttpHost,参数1是ip  参数2是端口号  参数3是通信协议
        RestHighLevelClient highLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        //自定义配置  结束
        return highLevelClient;
    }
}
