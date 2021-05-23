package cn.fantasyblog.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 测试用Service
 * @Author Cy
 * @Date 2021/5/23 17:12
 */
public interface TestService {

    /**
     * 测试Spring异步任务
     */
    void execute();

    /**
     * 测试Spring定时任务
     */
    void schedule();
}
