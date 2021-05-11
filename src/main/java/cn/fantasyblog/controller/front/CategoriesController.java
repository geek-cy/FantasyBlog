package cn.fantasyblog.controller.front;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.entity.Category;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-30 16:36
 */
@Api("前台：分类页面")
@Controller
public class CategoriesController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("访问分类页面")
    @AccessLog("访问分类页面")
    @GetMapping("/categories")
    public ResponseEntity<Object> categories(){
        List<Category> categories = categoryService.listArticleCountByCategory();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @ApiOperation("查询分类文章")
    @AccessLog("查询分类文章")
    @GetMapping("/category/{categoryId}/articles")
    public ResponseEntity<Object> categories(@PathVariable("categoryId") Long categoryId,
                                             @RequestParam(value = "current",defaultValue = Constant.PAGE)Integer current,
                                             @RequestParam(value = "size",defaultValue = Constant.PAGE_SIZE) Integer size){
        Page<Article> pageInfo = articleService.listPreviewPageByCategoryId(current,size, categoryId);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }
}
