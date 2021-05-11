package cn.fantasyblog.task;

import cn.fantasyblog.service.LikeService;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-07 16:07
 */

@Slf4j
public class Task extends QuartzJobBean {

    @Autowired
    private RedisService redisService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ViewService viewService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Task--------- {}",sdf.format(new Date()));
        // 将 Redis 里的点赞信息同步到数据库
        likeService.transLikedCount(true);
        likeService.transLiked();
        // 将 Redis 里的浏览量同步到数据库
        viewService.transViewCount(true);
    }

}
