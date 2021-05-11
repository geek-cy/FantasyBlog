package cn.myBlog.service.impl;

import cn.myBlog.common.Constant;
import cn.myBlog.common.TableConstant;
import cn.myBlog.dao.ArticleMapper;
import cn.myBlog.dao.ElasticMapper;
import cn.myBlog.dto.ArticleDocument;
import cn.myBlog.entity.Article;
import cn.myBlog.service.ElasticSearchService;
import cn.myBlog.utils.HighLightUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description es工具类
 * @Author Cy
 * @Date 2021-05-09 20:17
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ElasticMapper elasticMapper;

    @Async
    @Override
    public void save(Article article) {
        ArticleDocument articleDocument = new ArticleDocument();
        BeanUtils.copyProperties(article, articleDocument);
        elasticMapper.save(articleDocument);
    }

    @Async
    @Override
    public void sync(){
        elasticMapper.deleteAll();
        List<Article> articles = articleMapper.selectList(null);
        ArrayList<ArticleDocument> articleDocuments = new ArrayList<>();
        for (Article article : articles) {
            ArticleDocument articleDocument = new ArticleDocument();
            articleDocument.setId(article.getId());
        }
    }

    @Async
    public void deleteById(Long id) {
        elasticMapper.deleteById(id);
    }

    @Async
    public void deleteAll(List<ArticleDocument> articleDocuments){
        elasticMapper.deleteAll(articleDocuments);
    }
    @Override
    public List<ArticleDocument> listByKeyword(String keyword) throws IOException {
        // 匹配查询
        TermQueryBuilder publishBuilder = QueryBuilders.termQuery(ArticleDocument.Table.PUBLISHED, true);
        TermQueryBuilder statusBuilder = QueryBuilders.termQuery(ArticleDocument.Table.STATUS, Constant.AUDIT_NOT_PASS);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(publishBuilder).mustNot(statusBuilder);
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false)
                .field(ArticleDocument.Table.TITLE)
                .field(ArticleDocument.Table.SUMMARY)
                .field(ArticleDocument.Table.CONTENT)
                .preTags(Constant.HIGH_LIGHT_PRE_TAGS)
                .postTags(Constant.HIGH_LIGHT_POST_TAGS);
        // 条件集成到sourceBuilder中
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询
//        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(ArticleDocument.Table.TITLE, ArticleDocument.Table.CONTENT, ArticleDocument.Table.SUMMARY, keyword);
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(ArticleDocument.Table.CONTENT, keyword);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.query(boolQueryBuilder).query(matchQueryBuilder).highlighter(highlightBuilder);
        // 执行搜索
        SearchRequest request = new SearchRequest(TableConstant.ARTICLE_DOCUMENT);
        request.source(sourceBuilder);
        // 获取搜索响应
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 解析结果
        List<ArticleDocument> articleDocuments = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();
            // 解析高亮字段
            HighLightUtil.parseField(hit,ArticleDocument.Table.TITLE,map);
            HighLightUtil.parseField(hit,ArticleDocument.Table.SUMMARY,map);
            HighLightUtil.parseField(hit,ArticleDocument.Table.CONTENT,map);

            /**
             * 1. object.toString()方法
             */
            ArticleDocument articleDocument = new ArticleDocument();
            articleDocument.setId(Long.valueOf(String.valueOf(map.get(ArticleDocument.Table.ID))));
            articleDocument.setTitle((String) map.get(ArticleDocument.Table.TITLE));
            articleDocument.setSummary((String) map.get(ArticleDocument.Table.SUMMARY));
            articleDocument.setContent((String) map.get(ArticleDocument.Table.CONTENT));
            articleDocuments.add(articleDocument);
        }
        return articleDocuments;
    }
}