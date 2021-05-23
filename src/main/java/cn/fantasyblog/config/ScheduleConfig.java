/*
package cn.fantasyblog.config;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

*/
/**
 * @Description
 * @Author Cy
 * @Date 2021/5/12 11:09
 *//*

@Slf4j
@Configuration
@EnableScheduling
public class    ScheduleConfig {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ViewService viewService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private VisitorService visitorService;

    @Scheduled(cron = "0 0 0 * * ?")
    private void resetElasticSearch() {
        log.info("每天0点重置elasticsearch");
        elasticSearchService.sync();
    }

    @Scheduled(fixedDelay=2*60*60*1000)
    private void transDataBase(){
        log.info("每两小时进行持久化");
        // 将 Redis 里的点赞信息同步到数据库
        likeService.transLikedCount(true);
        likeService.transLiked();
        // 将 Redis 里的浏览量同步到数据库
        viewService.transViewCount(true);
        // 将 Redis 里的评论量同步到数据库
        commentService.transCommentCount(true);
    }

    @Scheduled(fixedDelay=3*60*60*1000)
    private void deleteVisitor(){
        log.info("三小时删除未激活访客");
        visitorService.removeVisitors();
    }
}
*/
