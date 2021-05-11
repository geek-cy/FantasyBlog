package cn.fantasyblog.service;

public interface LikeService {

    /**
     * 根据文章ID查询点赞数
     */
    Long count(Long id);

    /**
     * 持久化点赞量
     */
    Integer transLikedCount(boolean flag);

    /**
     * 持久化赞对象
     */
    void transLiked();

    /**
     * 判断是否已经赞过
     */
    void hasLiked(Long articleId,Long visitorId);
}
