package cn.fantasyblog.config;

import cn.fantasyblog.Quartz.ElasticQuartz;
import cn.fantasyblog.Quartz.RedisQuartz;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @Description 定时任务
 * @Author Cy
 * @Date 2021-05-06 23:39
 */

/**
 * 涉及的类：
 * 1、Scheduler 核心调度工具，底层已经实例化好了
 * 2、Job 接口定义任务即做什么
 * 3、JobDetail 配置Job相关参数
 * 4、Trigger 触发器 定义什么时候运行以及运行频率
 */
// 配置->数据库->调用
@Configuration
public class QuartzConfig {

    // FactoryBean可简化Bean的实例化过程
    // 通过FactoryBean封装Bean的实例化过程
    // 将FactoryBean装配到Spring容器中
    // 将FactoryBean注入给其他的Bean
    // 将Bean得到的是FactoryBean所管理的对象实例
    @Bean
    public JobDetailFactoryBean simpleDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(RedisQuartz.class);
        factoryBean.setName("redis");
        factoryBean.setGroup("redisGroup");
        factoryBean.setDurability(true);// 是否持久保存
        factoryBean.setRequestsRecovery(true);// 是否可恢复
        return factoryBean;
    }

    // 配置Trigger(SimpleTriggerFactoryBean,CronTriggerFactoryBean)
    // 形参名字尽量对应，以便多的JobDetail时能优先注入
    @Bean
    public SimpleTriggerFactoryBean simpleTrigger(JobDetail simpleDetail){
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(simpleDetail);
        factoryBean.setName("simple");
        factoryBean.setGroup("simpleGroup");
        factoryBean.setRepeatInterval(2*60*60*1000);// 频率
        factoryBean.setJobDataMap(new JobDataMap());// 存储状态
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean cronDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(ElasticQuartz.class);
        factoryBean.setName("elastic");
        factoryBean.setGroup("elasticGroup");
        factoryBean.setDurability(true);// 是否持久保存
        factoryBean.setRequestsRecovery(true);// 是否可恢复
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger(JobDetail cronDetail){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(cronDetail);
        factoryBean.setName("cron");
        factoryBean.setGroup("cronGroup");
        factoryBean.setCronExpression("0 0 0 * * ?");
//        factoryBean.setTimeZone();
        factoryBean.setJobDataMap(new JobDataMap());// 存储状态
        return factoryBean;
    }
}
