package cn.fantasyblog;

import cn.fantasyblog.dao.ArticleMapper;
import cn.fantasyblog.dao.VisitorMapper;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.filter.SensitiveFilter;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.MailService;
import cn.fantasyblog.service.VisitorService;
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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VisitorService visitorService;

    @Test
    void contextLoads() {
        visitorService.removeVisitors();
    }

}
