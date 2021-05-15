package cn.fantasyblog;

import cn.fantasyblog.dao.ArticleMapper;
import cn.fantasyblog.dao.VisitorMapper;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.filter.SensitiveFilter;
import cn.fantasyblog.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
class FantasyBlogApplicationTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private VisitorMapper visitorMapper;

    @Test
    void contextLoads() {
        String text = "这里是陈宇、陈杨翰宇、陈琪宇陈";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }

}
