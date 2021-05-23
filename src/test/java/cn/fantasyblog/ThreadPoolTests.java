package cn.fantasyblog;

import cn.fantasyblog.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/23 15:52
 */
@RunWith(SpringRunner.class)// 才能实例化到spring容器中，自动注入才能生效
@SpringBootTest
@Slf4j
public class ThreadPoolTests {

    // JDK线程池(虽然一半不推荐这样创建)
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    // JDK可执行定时任务的线程池(用的少了解即可)
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    // Spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    // Spring可执行定时任务线程池
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private TestService testService;

    @Autowired
    private Scheduler scheduler;

    private void sleep(long m){
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 普通线程池
    @Test
    public void testExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                log.info("Hello");
            }
        };

        for(int i = 0;i < 10;i++){
            executorService.submit(task);
        }
        sleep(10000);
    }

    // JDK定时任务线程池
    @Test
    public void taskScheduledExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                log.debug("Hello");
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(task,1,1, TimeUnit.SECONDS);
        sleep(20000);
    }

    // Spring普通线程池
    @Test
    public void testThreadPoolTaskExecutor(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                log.debug("Hello");
            }
        };
        for(int i = 0;i < 10;i++){
            taskExecutor.submit(task);
        }
        sleep(10000);
    }

    // Spring定时任务线程池
    @Test
    public void testThreadPoolTaskScheduler(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                log.debug("Hello");
            }
        };
        Date date = new Date(System.currentTimeMillis() + 10000);
        taskScheduler.scheduleAtFixedRate(task,date,1000);
        sleep(30000);
    }

    // 简化Spring普通线程池方法
    @Test
    public void testThreadPoolExecutor(){
        for(int i = 0;i < 10;i++){
            testService.execute();
        }
        sleep(10000);
    }

    // 简化Spring定时线程池方法
    @Test
    public void testThreadPoolScheduler(){
        sleep(40000);
        for(int i = 0;i < 10;i++){
            testService.schedule();
        }
    }

    @Test
    public void testDeleteJob() {
        try {
            boolean result = scheduler.deleteJob(new JobKey("task", "taskGroup"));
            System.out.println(result);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
