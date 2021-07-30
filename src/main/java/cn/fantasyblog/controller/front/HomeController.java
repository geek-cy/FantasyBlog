package cn.fantasyblog.controller.front;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.vo.HomeVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-28 14:07
 */
@Api(tags ="前台：首页")
@RestController
public class HomeController {

    @Autowired
    ArticleService articleService;

/*    @Autowired
    ElasticSearchService elasticSearchService;*/

    @ApiOperation("查询首页数据")
    @AccessLog("访问首页")
    @GetMapping("/home")
    public ResponseEntity<Object> home() {
        HomeVO homeVO = new HomeVO();
        homeVO.setTopArticles(articleService.listTop());
        homeVO.setRecommendArticles(articleService.listRecommend());
        homeVO.setPageInfo(articleService.listPreviewByPage(Integer.parseInt(Constant.PAGE), Integer.parseInt(Constant.PAGE_SIZE)));
        return new ResponseEntity<>(homeVO, HttpStatus.OK);
    }

    @ApiOperation("查询文章")
    @AccessLog("查询文章")
    @GetMapping("/articles")
    public ResponseEntity<Object> articles(@RequestParam(value = "current", defaultValue = Constant.PAGE) Integer current,
                                           @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Article> pageInfo = articleService.listPreviewByPage(current, size);
        System.out.println(pageInfo.getPages());
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

    /*@ApiOperation("搜索文章")
    @GetMapping("/articles/search")
    public ResponseEntity<Object> search(@RequestParam(value = "keyword")String keyword){
        List<ArticleDocument> articleDocuments;
        try {
            articleDocuments = elasticSearchService.listByKeyword(keyword);
        } catch (IOException e){
            e.printStackTrace();
            throw new BadRequestException("搜索失败");
        }
        return new ResponseEntity<>(articleDocuments,HttpStatus.OK);
    }*/

}
