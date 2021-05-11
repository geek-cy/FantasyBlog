package cn.myBlog.service.impl;

import cn.myBlog.dao.ArticleMapper;
import cn.myBlog.entity.Article;
import cn.myBlog.service.RedisService;
import cn.myBlog.service.ViewService;
import cn.myBlog.dto.ViewCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-08 14:46
 */
@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer transViewCount(boolean flag) {
        List<ViewCount> list = redisService.getViewCountFromRedis();
        for (ViewCount dto : list) {
            Article article = articleMapper.selectById(dto.getKey());
            // 点赞数量属于无关紧要的操作，出错无需抛异常
            if (article != null) {
                Integer viewNum = dto.getCount();
                if (!flag) return viewNum;
                article.setViews(article.getViews() + viewNum);
                // 更新浏览数量
                articleMapper.updateById(article);
                redisService.deleteView(article.getId());
            }
        }
        return 0;
    }
}
