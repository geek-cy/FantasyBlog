package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * @Description
 * @Author Cy
 * @Date 2021-05-06 19:46
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public void saveLiked(Long articleId, Long visitorId) {
        try {
            redisTemplate.opsForSet().add(Constant.LIKE_COUNT+"::"+articleId,visitorId);
        } catch (Exception e){
            log.error("redis错误");
        }
    }

    @Override
    public void incrementView(Long articleId) {
        try {
            redisTemplate.opsForValue().increment(Constant.VIEW_COUNT+"::"+articleId);
        } catch (Exception e) {
            log.error("redis错误");
        }
    }

    @Override
    public Long getViewCount(Long articleId) {
        try {
            Object o = redisTemplate.opsForValue().get(Constant.VIEW_COUNT + "::" + articleId);
            if(o == null) return 0L;
            return Long.valueOf(o.toString());
        } catch (Exception e) {
            log.error("redis错误");
            return 0L;
        }
    }

    @Override
    public void incrementComment(Long articleId, Long visitorId) {
        try {
            redisTemplate.opsForSet().add(Constant.COMMENT_COUNT+"::"+articleId,visitorId);
        } catch (Exception e) {
            log.error("redis错误");
        }
    }

    @Override
    public Long pv() {
        return redisTemplate.opsForValue().increment("blog::pv"/*+ date.format(new Date())*/);
    }

    @Override
    public Long uv(String ip) {
        redisTemplate.opsForHyperLogLog().add("blog::uv", ip);
        return redisTemplate.opsForHyperLogLog().size("blog::uv");
    }

    @Override
    public void deleteComment(Long articleId) {
        try {
            redisTemplate.opsForSet().remove(Constant.COMMENT_COUNT+"::"+articleId);
        } catch (Exception e) {
            log.error("redis错误");
        }
    }

    @Override
    public Long getCommentCount(Long articleId) {
        try {
            return redisTemplate.opsForSet().size(Constant.COMMENT_COUNT+"::"+articleId);
        } catch (Exception e) {
            log.error("redis错误");
            return 0L;
        }
    }

    @Override
    public Long getLikedCount(Long articleId) {
        try {
            return redisTemplate.opsForSet().size(Constant.LIKE_COUNT+"::"+articleId);
        } catch (Exception e) {
            log.error("redis错误");
            return 0L;
        }
    }

    @Override
    public void removeKey(){
        Set<Object> keys1 = redisTemplate.keys(Constant.LIKE_COUNT + "*");
        Set<Object> keys2 = redisTemplate.keys(Constant.COMMENT_COUNT + "*");
        Set<Object> keys3 = redisTemplate.keys(Constant.VIEW_COUNT + "*");
        if(keys1 != null) redisTemplate.delete(keys1);
        if(keys2 != null) redisTemplate.delete(keys2);
        if(keys3 != null) redisTemplate.delete(keys3);
    }

}
