package cn.fantasyblog.Quartz;

import cn.fantasyblog.service.ElasticSearchService;
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
 * @Date 2021/5/23 22:06
 */
@Slf4j
public class ElasticQuartz extends QuartzJobBean {

    @Autowired
    private ElasticSearchService elasticSearchService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("每天0点重置elasticsearch:{}",sdf.format(new Date()));
        elasticSearchService.sync();
    }
}
