package cn.myBlog.dao;

import cn.myBlog.entity.Comment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 后台分页查询所有评论
     *
     * @param page
     * @param wrapper
     * @return 评论分页
     */
    Page<Comment> listTableByPage(IPage<Comment> page, @Param("ew") QueryWrapper<Comment> wrapper);

    /**
     * 前台根据文章ID分页查询顶级评论
     * @param page
     * @param articleId
     * @return 评论分页
     */
    Page<Comment> listRootPageByArticleId(IPage<Comment> page,@Param("articleId") Long articleId);

    /**
     * 前台根据文章Id查询所有评论
     * @param articleId
     * @return 评论列表
     */
    List<Comment> listByArticleId(@Param("articleId")Long articleId);

    /**
     * 后台主页查询最近评论
     *
     * @param limit 显示数量
     * @return 评论列表
     */
    List<Comment> listNewest(@Param("limit")Integer limit);


}
