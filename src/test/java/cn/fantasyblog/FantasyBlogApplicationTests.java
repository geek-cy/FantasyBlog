package cn.fantasyblog;

import cn.fantasyblog.dao.ArticleMapper;
import cn.fantasyblog.dao.VisitorMapper;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.filter.SensitiveFilter;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.MailService;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.VisitorService;
import com.google.common.hash.BloomFilter;
import io.rebloom.client.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FantasyBlogApplicationTests {

    /*@Autowired
    private RedisService redisService;

    Client client = new Client("127.0.0.1", 6379);

    @Test
    void contextLoads() {
        client.addMulti("article", String.valueOf(1),String.valueOf(2),String.valueOf(3));
        System.out.println(client.exists("article", String.valueOf(1)));
    }*/

}
