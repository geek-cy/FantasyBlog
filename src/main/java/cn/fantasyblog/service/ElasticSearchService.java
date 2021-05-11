package cn.myBlog.service;

import cn.myBlog.entity.Article;
import cn.myBlog.dto.ArticleDocument;

import java.io.IOException;
import java.util.List;

public interface ElasticSearchService {

    /**
     * 保存索引
     */
    void save(Article article);

    /**
     * 删除索引
     */
    void deleteById(Long id);

    /**
     * 批量添加索引
     */
    void sync();
    /**
     * 搜索关键词
     */
    List<ArticleDocument> listByKeyword(String keyword) throws IOException;
}
