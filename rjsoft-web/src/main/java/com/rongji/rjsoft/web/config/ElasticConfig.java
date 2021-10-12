package com.rongji.rjsoft.web.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Elasticsearch
 * @author: JohnYehyo
 * @create: 2021-10-09 15:51:30
 */
@Configuration
public class ElasticConfig {

    /**
     * ES地址,ip:port
     */
    @Value("${elasticsearch.ip}")
    String hostList;

    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(getHttpHostList(hostList));
    }


    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        restClientBuilder.setMaxRetryTimeoutMillis(60000);
        return new RestHighLevelClient(restClientBuilder);
    }


    private HttpHost[] getHttpHostList(String hostList) {
        String[] hosts = hostList.split(",");
        HttpHost[] httpHostArr = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] items = hosts[i].split(":");
            httpHostArr[i] = new HttpHost(items[0], Integer.parseInt(items[1]), "http");
        }
        return httpHostArr;
    }
}
