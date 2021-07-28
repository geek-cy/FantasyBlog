package cn.fantasyblog.Initialize;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.filter.SensitiveFilter;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.ElasticSearchService;
import cn.fantasyblog.service.RedisService;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import io.rebloom.client.Client;
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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisService redisService;

    Client client = new Client("127.0.0.1", 6379);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        elasticSearchService.sync();
        sensitiveFilter.init();
        long l = articleService.getMaxId();
        for(long i = 0;i<=l;i++){
            client.add(Constant.bloomArticleId, String.valueOf(i));
        }
        // 部署时报空指针，原因暂未知
        articleService.sync();
//        redisService.removeKey();
    }
}
