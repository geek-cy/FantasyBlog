package cn.fantasyblog.service.impl;

import cn.fantasyblog.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Description 测试
 * @Author Cy
 * @Date 2021/5/23 17:12
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Override
    @Async
    public void execute() {
        log.debug("hello1");
    }

    @Override
//    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    public void schedule() {
        log.debug("hello2");
    }

}
