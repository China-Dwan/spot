package springboottest.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESRestClientConfig {

    @Bean
    public RestClient getClient() {
        //  如果有多个节点可以持续在内部new多个HttpHost,参数1是ip  参数2是端口号  参数3是通信协议
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

        //自定义配置  结束
        return clientBuilder.build();
    }
}
