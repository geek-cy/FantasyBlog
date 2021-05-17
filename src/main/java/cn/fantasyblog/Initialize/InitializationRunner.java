package cn.fantasyblog.Initialize;

import cn.fantasyblog.filter.SensitiveFilter;
import cn.fantasyblog.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Description 项目启动时的初始化操作
 * @Author Cy
 * @Date 2021/5/12 11:45
 */
@Component
public class InitializationRunner implements ApplicationRunner {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        elasticSearchService.sync();
        sensitiveFilter.init();
    }
}
