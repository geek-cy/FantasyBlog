package cn.myBlog.service;

import cn.myBlog.entity.Like;
import cn.myBlog.dto.LikedCount;
import cn.myBlog.dto.ViewCount;

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
     * 获得点赞量
     */
    List<LikedCount> getLikedCountFromRedis();

    /**
     * 获得浏览量
     */
    List<ViewCount> getViewCountFromRedis();

    /**
     * 获得赞对象
     */
    List<Like> getLikedDataFromRedis();


}
