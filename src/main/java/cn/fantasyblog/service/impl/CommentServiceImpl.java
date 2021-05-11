package cn.myBlog.service.impl;

import cn.myBlog.common.Constant;
import cn.myBlog.common.TableConstant;
import cn.myBlog.dao.ArticleMapper;
import cn.myBlog.dao.CommentMapper;
import cn.myBlog.dao.UserMapper;
import cn.myBlog.dao.VisitorMapper;
import cn.myBlog.entity.Article;
import cn.myBlog.entity.Comment;
import cn.myBlog.entity.User;
import cn.myBlog.entity.Visitor;
import cn.myBlog.service.CommentService;
import cn.myBlog.vo.AuditVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.query.CommentQuery;
import cn.myBlog.utils.LinkedListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-02 18:19
 */
@Service
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    VisitorMapper visitorMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    @Cacheable
    public Page<Comment> listTableByPage(Integer current, Integer size, CommentQuery commentQuery) {
        Page<Comment> page = new Page<>(current,size);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if (commentQuery.getStartDate() != null && commentQuery.getEndDate() != null) {
            wrapper.between(TableConstant.CATE_ALIAS+Comment.Table.CREATE_TIME, commentQuery.getStartDate(), commentQuery.getEndDate());
        }
        if (commentQuery.getStatus() != null) {
            wrapper.eq(TableConstant.CATE_ALIAS+Comment.Table.STATUS,commentQuery.getStatus());
        }
        return commentMapper.listTableByPage(page,wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        decrease(id);
        commentMapper.deleteById(id);
    }

    /**
     * 根据评论id查询评论类里的作者id
     * 根据评论里的作者id查询文章评论量和文章id
     * 评论量-1
     * @param id
     */
    public void decrease(Long id) {
        QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
        commentWrapper.select(Comment.Table.ARTICLE_ID).eq(Comment.Table.ID, id);
        Comment comment = commentMapper.selectOne(commentWrapper);
        QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
        articleWrapper.select(Article.Table.ID, Article.Table.COMMENTS).eq(Article.Table.ID, comment.getArticleId());
        Article article = articleMapper.selectOne(articleWrapper);
        article.setComments(article.getComments() - 1);
        articleMapper.updateById(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        for(Long id:idList){
            decrease(id);
        }
        commentMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void save(Comment comment) {
        // 评论量+1
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.Table.ID, Article.Table.COMMENTS,Article.Table.AUTHOR_ID).eq(Article.Table.ID, comment.getArticleId());
        Article article = articleMapper.selectOne(wrapper);
        article.setComments(article.getComments() + 1);
        comment.setUserId(article.getAuthorId());
//        article.setId()
        commentMapper.insert(comment);
        articleMapper.updateById(article);
    }

    @Override
    @Cacheable
    public Page<Comment> listByArticleId(Long articleId, Integer current, Integer size) {
        Page<Comment> page = new Page<>(current,size);
        Page<Comment> pageInfo = commentMapper.listRootPageByArticleId(page,articleId);
        List<Comment> comments = commentMapper.listByArticleId(articleId);
        LinkedListUtil.toCommentLinkedList(pageInfo.getRecords(),comments);
        return pageInfo;
    }

    /** 若回复父级评论存在
     * 根据评论获取访客id
     * 根据访客id获取访客昵称
     * 评论昵称设置为访客昵称
     * 若不存在
     * 评论昵称设置为用户昵称
     * @param comment 评论
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void reply(Comment comment) {
        if(comment.getVisitorId() != null){
            QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
            commentWrapper.select(Comment.Table.VISITOR_ID).eq(Comment.Table.ID,comment.getPid());
            Comment parentComment = commentMapper.selectOne(commentWrapper);
            QueryWrapper<Visitor> visitorWrapper = new QueryWrapper<>();
            visitorWrapper.select(Visitor.Table.NICKNAME).eq(Visitor.Table.ID,parentComment.getVisitorId());
            Visitor visitor = visitorMapper.selectOne(visitorWrapper);
            comment.setParentNickname(visitor.getNickname());
        } else{
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.select(User.Table.NICKNAME).eq(User.Table.ID,comment.getUserId());
            User user = userMapper.selectOne(wrapper);
//            comment.setVisitorId();
            comment.setParentNickname(user.getNickname());
        }
        commentMapper.insert(comment);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void audit(AuditVO auditVO) {
        Comment comment = new Comment();
        comment.setId(auditVO.getId());
        comment.setStatus(auditVO.getStatus());
        commentMapper.updateById(comment);
    }

    @Override
    @Cacheable
    public Long countAll() {
        return  Long.valueOf(commentMapper.selectCount(null));
    }

    /**
     * 根据时间显示近10条评论，包括id、昵称、内容、时间、文章id、审核状态
     * @return
     */
    @Override
    @Cacheable
    public List<Comment> listNewest() {
        return commentMapper.listNewest(Constant.NEWEST_PAGE_SIZE);
    }
}