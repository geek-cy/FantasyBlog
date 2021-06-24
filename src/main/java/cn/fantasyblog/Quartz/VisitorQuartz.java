package cn.fantasyblog.Quartz;

import cn.fantasyblog.entity.Article;
import cn.fantasyblog.service.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description redis定时持久化任务
 * @Author Cy
 * @Date 2021-05-07 16:07
 */

@Slf4j
public class VisitorQuartz extends QuartzJobBean {

    @Autowired
    private VisitorService visitorService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Long index = 0L;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("VisitorQuartz--------- {}",sdf.format(new Date()));
        log.info("删除未激活访客");
        visitorService.removeVisitors();
    }
}

