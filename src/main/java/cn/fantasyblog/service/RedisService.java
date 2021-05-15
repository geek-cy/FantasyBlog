package cn.fantasyblog.service;

import cn.fantasyblog.dto.CommentCount;
import cn.fantasyblog.entity.Comment;
import cn.fantasyblog.entity.Like;
import cn.fantasyblog.dto.LikedCount;
import cn.fantasyblog.dto.ViewCount;

import java.util.List;

public interface RedisService {

    /**
     * 点赞状态为1且点赞数+1
     */
    void saveLiked(Long articleId,Long visitorId);

    /**
     * 点赞状态为0
     */
    void unLiked(Long articleId,Long visitorId);

    /**
     * 文章浏览量+1
     */
    void incrementView(Long articleId);

    /**
     * 删除点赞量
     */
    void deleteLiked(Long articleId);

    /**
     * 删除点赞访客键
     */
    void deleteLikedMap(Long articleId,Long visitorId);

    /**
     * 删除浏览量
     */
    void deleteView(Long articleId);

    /**
     * 删除评论量
     */
    void deleteComment(Long articleId);

    /**
     * 获得点赞量
     */
    List<LikedCount> getLikedCountFromRedis();

    /**
     * 获得浏览量
     */
    List<ViewCount> getViewCountFromRedis();

    /**
     * 获得评论量
     */
    List<CommentCount> getCommentCountFromRedis();

    /**
     * 获得赞对象
     */
    List<Like> getLikedDataFromRedis();

    /**
     * 删除菜单缓存
     */
    void deleteMenu();

    /**
     * 评论量+1
     */
    void incrementComment(Long articleId);

}
