package cn.fantasyblog.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-09 20:30
 */
/**
 * ElasticsearchRestTemplate是spring-data-elasticsearch项目中的一个类,和其他spring项目中的 template类似。
 * 在新版的spring-data-elasticsearch 中，ElasticsearchRestTemplate 代替了原来的ElasticsearchTemplate。
 * 原因是ElasticsearchTemplate基于TransportClient，TransportClient即将在8.x 以后的版本中移除。所以，我们推荐使用ElasticsearchRestTemplate。
 * ElasticsearchRestTemplate基于RestHighLevelClient客户端的。需要自定义配置类，继承AbstractElasticsearchConfiguration，并实现elasticsearchClient()抽象方法，创建RestHighLevelClient对象。
 */
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "elasticsearch")
//@EnableConfigurationProperties(ElasticProperties.class)
@Configuration
@Data
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private String host;
    private Integer port;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
        return new RestHighLevelClient(builder);
    }
}
