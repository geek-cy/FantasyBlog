package cn.myBlog.service;

import cn.myBlog.entity.Article;
import cn.myBlog.vo.ArticleDateVO;
import cn.myBlog.vo.AuditVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.query.ArticleQuery;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    /**
     * 保存或更新文章
     * @param article 文章
     */
    void saveOrUpdate(Article article);

    /**
     * 根据ID查询文章
     * @param id 文章Id
     * @return 文章
     */
    Article getById(Long id);

    /**
     * 分页查询文章
     *
     * @param current      当前页码
     * @param size         页面大小
     * @param articleQuery 条件
     * @return 文章列表
     */
    Page<Article> listTableByPage(Integer current, Integer size, ArticleQuery articleQuery);

    /**
     * 审核文章
     */
    void audit(AuditVO auditVO);

    /**
     * 查询推荐文章
     *
     * @return 推荐文章列表
     */
    List<Article> listRecommend();

    /**
     * 查询置顶文章
     * @return
     */
    List<Article> listTop();

    /**
     * 分页查询所有文章
     *
     * @param current 当前页码
     * @param size    页码大小
     * @return 文章列表
     */
    Page<Article> listPreviewByPage(Integer current, Integer size);

    /**
     * 根据ID查询文章详情
     * @param id 文章ID
     * @return
     */
    Article getDetailById(Long id);

    /**
     * 获取当前文章的上一篇文章预览
     * @param id
     * @return
     */
    Article getPrevPreviewById(Long id);

    /**
     * 获取当前文章的下一篇文章预览
     * @param id
     * @return
     */
    Article getNextPreviewById(Long id);

    /**
     * 后台删除文章
     * @param id
     */
    void remove(Long id);

    /**
     * 后台批量删除文章
     * @param idList
     */
    void removeList(List<Long> idList);


    /**
     * 查询记录总数
     * 面板数据和关于我会用到
     * @return 记录总数
     */
    long countAll();

    /**
     * 前台根据分类ID分页获取分类的所有文章
     * @param categoryId 分类ID
     * @return 文章列表
     */
    Page<Article> listPreviewPageByCategoryId(Integer current, Integer size, Long categoryId);

    /**
     * 前台根据标签ID分页获取标签的所有文章
     *
     * @param tagId 标签ID
     * @return 文章列表
     */
    Page<Article> listPreviewPageByTagId(Integer current, Integer size, Long tagId);


    /**
     * 前台根据日期统计文章数量
     *
     * @param dateFilterType
     * @return 文章日期统计
     */
    List<ArticleDateVO> countByDate(Integer dateFilterType);

    /**
     * 根据日期分页获取所有文章预览
     *
     * @param current       当前页
     * @param size          页面大小
     * @return 文章预览列表
     */
    Page<Article> listPreviewPageByDate(Integer current, Integer size);

    /**
     * 查询最近的文章
     *
     * @return 文章列表
     */
    List<Article> listNewest();
}
