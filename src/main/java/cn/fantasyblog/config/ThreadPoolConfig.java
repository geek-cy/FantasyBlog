package cn.fantasyblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/23 16:28
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
