package cn.fantasyblog.service;

import cn.fantasyblog.entity.Article;
import cn.fantasyblog.dto.ArticleDocument;
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
     * 批量删除索引
     */
    void deleteAll(List<ArticleDocument> articleDocuments);

    /**
     * 批量添加索引
     */
    void sync();

    /**
     * 搜索关键词
     */
    List<ArticleDocument> listByKeyword(String keyword) throws IOException;
}
