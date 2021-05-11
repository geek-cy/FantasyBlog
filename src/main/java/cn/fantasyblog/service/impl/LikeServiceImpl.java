package cn.fantasyblog.service.impl;

import cn.fantasyblog.dao.ArticleMapper;
import cn.fantasyblog.dao.LikeMapper;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.entity.Like;
import cn.fantasyblog.service.LikeService;
import cn.fantasyblog.service.RedisService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.fantasyblog.dto.LikedCount;
import cn.fantasyblog.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-06 22:03
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transLiked() {
        List<Like> list = redisService.getLikedDataFromRedis();
        for (Like like : list) {
            hasLiked(like.getArticleId(),like.getVisitorId());
            like.setCreateTime(new Date());
            like.setUpdateTime(new Date());
            likeMapper.insert(like);
            redisService.deleteLikedMap(like.getArticleId(),like.getVisitorId());
        }
    }

    @Override
    public void hasLiked(Long articleId,Long visitorId){
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.select(Like.Table.ID, Like.Table.STATUS).eq(Like.Table.ARTICLE_ID, articleId).eq(Like.Table.VISITOR_ID, visitorId);
        Like one = likeMapper.selectOne(wrapper);
        if (one != null && one.getStatus().equals(1)) {
            throw new BadRequestException("您已赞过");
        }
    }

    @Override
    public Long count(Long id) {
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq(Like.Table.ARTICLE_ID, id);
        return Long.valueOf(likeMapper.selectCount(wrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer transLikedCount(boolean flag) {
        List<LikedCount> list = redisService.getLikedCountFromRedis();
        for (LikedCount dto : list) {
            Article article = articleMapper.selectById(dto.getKey());
            // 点赞数量属于无关紧要的操作，出错无需抛异常
            if (article != null) {
                Integer likeNum = dto.getCount();
                if (!flag) return likeNum;
                article.setLikes(article.getLikes() + likeNum);
                // 更新点赞数量
                articleMapper.updateById(article);
                redisService.deleteLiked(article.getId());
            }
        }
        return 0;
    }
}
