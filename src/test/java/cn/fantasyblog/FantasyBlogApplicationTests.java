package cn.fantasyblog;

import cn.fantasyblog.dao.ArticleMapper;
import cn.fantasyblog.dao.ElasticMapper;
import cn.fantasyblog.dao.VisitorMapper;
import cn.fantasyblog.dto.ArticleDocument;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FantasyBlogApplicationTests {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void contextLoads() {
        Article article = articleMapper.selectPrevPreviewById(10L);
        System.out.println(article);
    }

}
