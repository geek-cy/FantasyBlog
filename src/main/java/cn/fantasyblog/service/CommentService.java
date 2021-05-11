package cn.fantasyblog.service;

import cn.fantasyblog.entity.Comment;
import cn.fantasyblog.vo.AuditVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.CommentQuery;

import java.util.List;

public interface CommentService {

    /**
     * 后台分页查询所有评论
     * @return
     */
    Page<Comment> listTableByPage(Integer current, Integer size, CommentQuery commentQuery);

    /**
     * 删除评论
     */
    void remove(Long id);

    /**
     * 批量删除评论
     * @param idList
     */
    void removeList(List<Long> idList);

    /**
     * 前台新增评论
     */
    void save(Comment comment);

    /**
     * 前台分页查询文章的所有评论
     *
     * @return 评论分页
     */
    Page<Comment> listByArticleId(Long articleId, Integer current, Integer size);

    /**
     * 根据ID回复评论
     *
     * @param comment 评论
     */
    void reply(Comment comment);

    /**
     * 根据ID审核评论
     */
    void audit(AuditVO auditVO);

    /**
     * 统计评论总数
     *
     * @return 评论总数
     */
    Long countAll();

    /**
     * 查询最近的评论
     *
     * @return 评论列表
     */
    List<Comment> listNewest();

    /**
     * 统计上次访问首页至现在增加的评论数
     */
//    Integer countByLastIndexViewToNow();
}
