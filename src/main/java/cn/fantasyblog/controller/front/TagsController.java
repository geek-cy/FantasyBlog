package cn.myBlog.controller.front;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.common.Constant;
import cn.myBlog.entity.Article;
import cn.myBlog.entity.Tag;
import cn.myBlog.service.ArticleService;
import cn.myBlog.service.TagService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-30 17:02
 */
@Api("前台：标签页面")
@RestController()
public class TagsController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @ApiOperation("访问标签页面")
    @AccessLog("访问标签页面")
    @GetMapping("/tags")
    public ResponseEntity<Object> tagCount() {
        List<Tag> tags = tagService.listArticleCountByTag();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @ApiOperation("分页查询标签")
    @AccessLog("分页查询标签")
    @GetMapping("/tag/{tagId}/articles")
    public ResponseEntity<Object> tags(@PathVariable("tagId") Long tagId,
                                       @RequestParam(value = "current", defaultValue = Constant.PAGE) Integer current,
                                       @RequestParam(value = "size", defaultValue = Constant.PAGE_SIZE) Integer size) {
        Page<Article> pageInfo = articleService.listPreviewPageByTagId(current, size, tagId);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }


}
