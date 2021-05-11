package cn.myBlog.dao;

import cn.myBlog.dto.ArticleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-09 20:34
 */
@Repository
public interface ElasticMapper extends ElasticsearchRepository<ArticleDocument, Long> {

}
