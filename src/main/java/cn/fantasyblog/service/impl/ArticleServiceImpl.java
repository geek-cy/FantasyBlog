package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.TableConstant;
import cn.fantasyblog.dao.ArticleMapper;
import cn.fantasyblog.dao.ArticleTagMapper;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.entity.ArticleTag;
import cn.fantasyblog.entity.Tag;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.vo.ArticleDateVO;
import cn.fantasyblog.vo.AuditVO;
import cn.hutool.core.io.resource.NoResourceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.ArticleQuery;
import io.rebloom.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-21 16:09
 */
@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private RedisService redisService;

    /*@Autowired
    private ElasticSearchService elasticSearchService;*/

    Client client = new Client("127.0.0.1", 6379);

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    @CachePut
    public void saveOrUpdate(Article article) {
        if (article.getId() == null) {
            // 新增
            articleMapper.insert(article);
            // 添加到布隆过滤器中
            client.add(Constant.bloomArticleId, String.valueOf(article.getId()));
        } else {
            // 更新
            // 更新文章信息
            articleMapper.updateById(article);
            // 删除原有标签
            QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
            articleTagWrapper.eq(ArticleTag.Table.ARTICLE_ID, article.getId());
            articleTagMapper.delete(articleTagWrapper);
            // 删除ElasticSearch索引
            /*if(elasticSearchService != null)
            elasticSearchService.deleteById(article.getId());*/
        }
        // 添加新标签
        List<Long> tagIdList = article.getTagList().stream().map(Tag::getId).collect(Collectors.toList());
        articleTagMapper.insertBatch(article.getId(), tagIdList);
        // 添加到ElasticSearch中
        /*if(elasticSearchService != null)
            elasticSearchService.save(article);*/
    }

    @Override
    public Article getById(Long id) {
        if(!client.exists(Constant.bloomArticleId,String.valueOf(id))) throw new NoResourceException("id不存在");
        return articleMapper.selectById(id);
    }

    @Override
    public Long getMaxId(){
        return articleMapper.maxId();
    }

    @Override
    @Cacheable
    public Page<Article> listTableByPage(Integer current, Integer size, ArticleQuery articleQuery) {
        Page<Article> page = new Page<>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(articleQuery.getTitle())) {
            wrapper.like(Article.Table.TITLE, articleQuery.getTitle());
        }
        if (articleQuery.getType() != null) {
            wrapper.eq(Article.Table.TYPE, articleQuery.getType());
        }
        if (articleQuery.getCategoryId() != null) {
            wrapper.eq(Article.Table.CATEGORY_ID, articleQuery.getCategoryId());
        }
        if (articleQuery.getPublished() != null) {
            wrapper.eq(Article.Table.PUBLISHED, articleQuery.getPublished());
        }
        if (articleQuery.getStatus() != null) {
            wrapper.eq(TableConstant.ARTICLE_ALIAS + Article.Table.STATUS, articleQuery.getStatus());
        }
        if (articleQuery.getTop() != null) {
            wrapper.eq(Article.Table.TOP, articleQuery.getTop());
        }
        if (articleQuery.getRecommend() != null) {
            wrapper.eq(Article.Table.RECOMMEND, articleQuery.getRecommend());
        }
        if (articleQuery.getStartDate() != null && articleQuery.getEndDate() != null) {
            wrapper.between(TableConstant.ARTICLE_ALIAS + Article.Table.CREATE_TIME, articleQuery.getStartDate(), articleQuery.getEndDate());
        }
        return articleMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void audit(AuditVO auditVO) {
        Article article = new Article();
        article.setId(auditVO.getId());
        article.setStatus(auditVO.getStatus());
        articleMapper.updateById(article);
        /*if(elasticSearchService != null)
            elasticSearchService.save(article);*/
    }

    @Override
    @Cacheable
    public List<Article> listRecommend() {
        return articleMapper.listPreviewRecommend(Constant.MAX_RECOMMEND_ARTICLES);
    }

    @Override
    @Cacheable
    public List<Article> listTop() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.Table.ID, Article.Table.TITLE, Article.Table.SUMMARY, Article.Table.COVER)
                .eq(Article.Table.PUBLISHED, true)
                .eq(Article.Table.TOP, true)
                .eq(Article.Table.STATUS, Constant.AUDIT_PASS)
                .orderByDesc(Article.Table.SORT)
                .last(TableConstant.LIMIT + Constant.MAX_TOP_ARTICLES);
        return articleMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public Page<Article> listPreviewByPage(Integer current, Integer size) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewByPage(articlePage);
    }

    @Override
    @Cacheable
    public Article getDetailById(Long id) {
        if(!client.exists(Constant.bloomArticleId,String.valueOf(id))) throw new NoResourceException("id不存在");
        return articleMapper.selectDetailById(id);
    }

    @Override
    @Cacheable
    public long countAll() {
        return articleMapper.selectCount(null);
    }

    @Override
    @Cacheable
    public Page<Article> listPreviewPageByCategoryId(Integer current, Integer size, Long categoryId) {
        Page<Article> page = new Page<>(current, size);
        return articleMapper.listPreviewPageByCategoryId(page, categoryId);
    }


    @Override
    @Cacheable
    public Page<Article> listPreviewPageByTagId(Integer current, Integer size, Long tagId) {
        Page<Article> page = new Page<>(current, size);
        return articleMapper.listPreviewPageByTagId(page, tagId);
    }

    @Override
    @Cacheable
    public List<ArticleDateVO> countByDate() {
        return articleMapper.countByDate();
    }

    @Override
    @Cacheable
    public Page<Article> listPreviewPageByDate(Integer current, Integer size) {
        Page<Article> page = new Page<>(current, size);
        return articleMapper.listPreviewByDate(page);
    }

    @Override
    @Cacheable
    public List<Article> listNewest() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.Table.ID, Article.Table.TITLE, Article.Table.SUMMARY, Article.Table.CREATE_TIME)
                .orderByDesc(Article.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return articleMapper.selectList(wrapper);
    }

    @Override
    public void sync() {
        long countAll =countAll();
        for(long i = 1;i <= countAll;i++){
            Long likedCount = redisService.getLikedCount(i);
            Long commentCount = redisService.getCommentCount(i);
            Long viewCount = redisService.getViewCount(i);
            Article article = getById(i);
            if(article == null) return;
            if(!article.getLikes().equals(likedCount)){
                article.setLikes(likedCount);
            }
            if(!article.getComments().equals(commentCount)){
                article.setComments(commentCount);
            }
            if(!article.getViews().equals(viewCount)){
                article.setViews(viewCount);
            }
            saveOrUpdate(article);
            articleMapper.updateById(article);
        }
    }

    @Override
    @Cacheable
    public Article getPrevPreviewById(Long id) {
        return articleMapper.selectPrevPreviewById(id);
    }

    @Override
    @Cacheable
    public Article getNextPreviewById(Long id) {
        return articleMapper.selectNextPreviewById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        articleMapper.deleteById(id);
        /*if(elasticSearchService != null)
            elasticSearchService.deleteById(id);*/
    }

    @Override
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        articleMapper.deleteBatchIds(idList);
        /*ArrayList<ArticleDocument> articleDocuments = new ArrayList<>();
        for (Long id : idList) {
            ArticleDocument articleDocument = new ArticleDocument();
            articleDocument.setId(id);
            articleDocuments.add(articleDocument);
        }
        if(elasticSearchService != null)
            elasticSearchService.deleteAll(articleDocuments);*/
    }

}
