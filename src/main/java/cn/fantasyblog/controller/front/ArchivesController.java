package cn.fantasyblog.controller.front;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.utils.DateUtil;
import cn.fantasyblog.vo.ArchivesVO;
import cn.fantasyblog.vo.ArticleDateVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @Date 2021-04-17 15:42
 */
@Api(tags ="前台：归档页面")
@RestController
public class ArchivesController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("查询归档页面数据")
    @AccessLog("查询归档页面数据")
    @GetMapping("/archives")
    public ResponseEntity<Object> archivesData(Integer dateFilterType){
        List<ArticleDateVO> articleDates = articleService.countByDate(dateFilterType);
        for (ArticleDateVO articleDate : articleDates) {
            articleDate.setDate(DateUtil.formatDate(articleDate.getYear(), articleDate.getMonth(), articleDate.getDay()));
            articleDate.setYear(null);
            articleDate.setMonth(null);
            articleDate.setDay(null);
        }
        Page<Article> page = articleService.listPreviewByPage(Integer.parseInt(Constant.PAGE), Integer.parseInt(Constant.PAGE_SIZE));
        ArchivesVO archivesVO = new ArchivesVO();
        archivesVO.setArticleDates(articleDates);
        archivesVO.setPageInfo(page);
        return new ResponseEntity<>(archivesVO, HttpStatus.OK);
    }

    @ApiOperation("查询文章数据")
    @AccessLog("查询文章数据")
    @GetMapping("/archives-articles")
    public ResponseEntity<Object> archivesArticles(@RequestParam(value = "current",defaultValue = Constant.PAGE)Integer current,
                                                   @RequestParam(value = "size",defaultValue = Constant.PAGE_SIZE)Integer size){
        Page<Article> pageInfo = articleService.listPreviewPageByDate(current, size);
        return new ResponseEntity<>(pageInfo,HttpStatus.OK);
    }
}
