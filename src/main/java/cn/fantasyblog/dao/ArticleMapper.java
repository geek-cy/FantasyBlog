package cn.fantasyblog.dao;

import cn.fantasyblog.vo.ArticleDateVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-21 15:49
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 后台分页查询文章
     * @param page 分页参数
     * @param queryWrapper 条件
     * @return 文章列表
     */
    Page<Article> listTableByPage(IPage<Article> page, @Param("ew")QueryWrapper<Article> queryWrapper);


    /**
     * 前台查询推荐文章
     * @param limit 最大限制
     * @return 文章列表
     */
    List<Article> listPreviewRecommend(@Param("limit") int limit);

    /**
     * 前台分页查询文章预览
     * @param page 分页参数
     * @return 预览列表
     */
    Page<Article> listPreviewByPage(IPage<Article> page);

    /**
     * 前台根据日期分页查询文章预览
     * @param page 分页参数
     * @return 预览列表
     */
    Page<Article> listPreviewByDate(IPage<Article> page);

    /**
     * 前台根据ID查询文章详情
     * @param id 文章ID
     * @return 文章详情
     */
    Article selectDetailById(Long id);

    /**
     * 前台获取当前文章的上一篇文章预览
     * @param id 当前文章ID
     * @return 上一篇文章预览
     */
    Article selectPrevPreviewById(@Param("id") Long id);

    /**
     * 前台获取当前文章的下一篇文章预览
     * @param id 当前文章ID
     * @return 下一篇文章预览
     */
    Article selectNextPreviewById(@Param("id") Long id);

    /**
     * 前台根据分类ID分页获取分类的所有文章
     * @param page
     * @param categoryId
     * @return
     */
    Page<Article> listPreviewPageByCategoryId(IPage<Article> page,@Param("categoryId") Long categoryId);

    /**
     * 前台根据标签ID分页获取标签的所有文章
     * @param page
     * @param tagId
     * @return
     */
    Page<Article> listPreviewPageByTagId(IPage<Article> page,@Param("tagId") Long tagId);

    /**
     * 前台根据日期统计文章数量
     * @param
     * @return
     */
    List<ArticleDateVO> countByDate();

    /**
     * 查询所有文章浏览量
     */
    Long countViews();

    /**
     * 获取文章id最大值
     */
    Long maxId();

    /**
     * 更新文章顺序
     */
    void updateID();
}
