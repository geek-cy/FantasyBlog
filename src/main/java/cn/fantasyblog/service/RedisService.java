package cn.fantasyblog.service;

public interface RedisService {

    /**
     * 点赞+1
     */
    void saveLiked(Long articleId,Long visitorId);

    /**
     * 获得点赞量
     */
    Long getLikedCount(Long articleId);

    /**
     * 文章浏览量+1
     */
    void incrementView(Long articleId);

    /**
     * 获得浏览量
     */
    Long getViewCount(Long id);

    /**
     * 删除评论量
     */
    void deleteComment(Long articleId);

    /**
     * 获得评论量
     * @return
     */
    Long getCommentCount(Long articleId);

    /**
     * 评论量+1
     */
    void incrementComment(Long articleId, Long visitorId);

    /**
     * 统计PV
     */
    Long pv();

    /**
     * 统计UV
     */
    Long uv(String ip);

    /**
     * 删除评论、点赞、访问键
     */
    void removeKey();


}
