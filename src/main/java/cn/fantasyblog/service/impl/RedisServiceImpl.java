package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dto.LikedCount;
import cn.fantasyblog.dto.ViewCount;
import cn.fantasyblog.entity.Like;
import cn.fantasyblog.service.LikeService;
import cn.fantasyblog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Description
 * @Author Cy
 * @Date 2021-05-06 19:46
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private LikeService likeService;

    @Override
    public void saveLiked(Long articleId, Long visitorId) {
        // 每次点赞都会遍历扫一遍map
        List<Like> list = getLikedDataFromRedis();
        likeService.hasLiked(articleId,visitorId);
        redisTemplate.opsForHash().put(Constant.LIKE_KEY, articleId + Constant.KEY + visitorId, 1);
        redisTemplate.opsForHash().put(Constant.LIKE_COUNT, articleId, list.size()==0?1:list.size());
    }

    @Override
    public void unLiked(Long articleId, Long visitorId) {
        redisTemplate.opsForHash().put(Constant.LIKE_KEY, articleId + Constant.KEY + visitorId, 0);
    }

    @Override
    public void deleteLiked(Long articleId) {
        redisTemplate.opsForHash().delete(Constant.LIKE_COUNT,articleId);
    }

    @Override
    public void deleteLikedMap(Long articleId,Long visitorId) {
        redisTemplate.opsForHash().delete(Constant.LIKE_KEY,articleId+Constant.KEY+visitorId);
    }

    @Override
    public void incrementView(Long articleId) {
        redisTemplate.opsForHash().increment(Constant.VIEW_COUNT, articleId, 1);
    }

    @Override
    public void deleteView(Long articleId) {
        redisTemplate.opsForHash().delete(Constant.VIEW_COUNT,articleId);
    }

    /**
     * 从redis中获取赞数
     */
    @Override
    public List<LikedCount> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(Constant.LIKE_COUNT, ScanOptions.NONE);
        List<LikedCount> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            // 注意这里key实际上是String类型
            Long key = Long.parseLong((String) map.getKey());
            // 将值保存到LikeCount中
            Integer like = (Integer) map.getValue();
            LikedCount dto = new LikedCount(key, like);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<Like> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(Constant.LIKE_KEY, ScanOptions.NONE);
        List<Like> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            // 分离出 articleId，visitorId
            String[] split = key.split(Constant.KEY);
            Long articleId = Long.parseLong(split[0]);
            Long visitorId = Long.parseLong(split[1]);
            Integer value = (Integer) entry.getValue();
            // 组装成 UserLike 对象
            Like userLike = new Like(articleId, visitorId, value);
            list.add(userLike);
        }
        return list;
    }

    @Override
    public void deleteMenu() {
        redisTemplate.delete(Constant.MENU);
    }

    @Override
    public List<ViewCount> getViewCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(Constant.VIEW_COUNT, ScanOptions.NONE);
        List<ViewCount> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            // 将浏览数量存储在 ViewCount
            Long key = Long.parseLong((String) map.getKey());
            ViewCount dto = new ViewCount(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

}
