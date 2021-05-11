package cn.fantasyblog.controller.front;



import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.CategoryService;
import cn.fantasyblog.service.PhotoService;
import cn.fantasyblog.service.TagService;
import cn.fantasyblog.utils.DateUtil;
import cn.fantasyblog.vo.AboutVO;
import cn.fantasyblog.vo.ArticleDateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-14 16:57
 */
@Api("前台：关于我页面")
@RestController
public class AboutController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PhotoService photoService;

    @ApiOperation("查询关于我页面数据")
    @AccessLog("查询关于我页面数据")
    @GetMapping("/about")
    public ResponseEntity<Object> about(@RequestParam(value = "dateType",required = false) Integer dateFilterType){
        AboutVO aboutVO = new AboutVO();
        aboutVO.setArticleCount(articleService.countAll());
        aboutVO.setCategoryCount(categoryService.countAll());
        aboutVO.setTagCount(tagService.countAll());
        aboutVO.setCategories(categoryService.listArticleCountByCategory());
        aboutVO.setTags(tagService.listArticleCountByTag());
        aboutVO.setPhotos(photoService.listAll());
        List<ArticleDateVO> articleDates = articleService.countByDate(dateFilterType);
        for (ArticleDateVO articleDate : articleDates) {
            articleDate.setDate(DateUtil.formatDate(articleDate.getYear(), articleDate.getMonth(), articleDate.getDay()));
            articleDate.setYear(null);
            articleDate.setMonth(null);
            articleDate.setDay(null);
        }
        aboutVO.setArticleDates(articleDates);
        return new ResponseEntity<>(aboutVO, HttpStatus.OK);
    }
}
