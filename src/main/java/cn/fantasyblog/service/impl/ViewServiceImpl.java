package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dao.ArticleMapper;
import cn.fantasyblog.dao.VisitorMapper;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.ViewService;
import cn.fantasyblog.dto.ViewCount;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-08 14:46
 */
@Service
@Slf4j
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
