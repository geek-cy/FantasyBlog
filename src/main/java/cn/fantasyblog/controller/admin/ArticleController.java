package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.Article;
import cn.fantasyblog.query.ArticleQuery;
import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.utils.UserInfoUtil;
import cn.fantasyblog.vo.AuditVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-21 18:42
 */
@Api(tags = "后台：文章管理")
@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("新增文章")
    @PreAuthorize("hasAuthority('blog:article:add')")
    @OperationLog("新增文章")
    @PostMapping
    public JsonResult save(@Validated @RequestBody Article article) {
        article.setViews(0)
                .setLikes(0)
                .setComments(0)
                .setCreateTime(new Date())
                .setUpdateTime(article.getCreateTime())
                .setAuthorId(UserInfoUtil.getUserId())
                .setStatus(Constant.AUDIT_WAIT);
        articleService.saveOrUpdate(article);
        return JsonResult.ok();
    }

    @ApiOperation("查询文章")
    @PreAuthorize("hasAuthority('blog:article:query')")
    @AccessLog("查询文章")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page", defaultValue = Constant.PAGE) Integer page,
                                       @RequestParam(value = "limit", defaultValue = Constant.PAGE_LIMIT) Integer limit,
                                       ArticleQuery articleQuery) {
        Page<Article> articlePage = articleService.listTableByPage(page, limit, articleQuery);
        return TableResult.tableOk(articlePage.getRecords(), articlePage.getTotal());
    }

    @ApiOperation("更新文章")
    @PreAuthorize("hasAuthority('blog:article:edit')")
    @OperationLog("更新文章")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Article article) {
        article.setUpdateTime(new Date());
        article.setStatus(Constant.AUDIT_WAIT);
        articleService.saveOrUpdate(article);
        return JsonResult.ok();
    }

    @ApiOperation("审核文章")
    @PreAuthorize("hasAuthority('blog:article:audit')")
    @OperationLog("审核文章")
    @PutMapping("/audit")
    public JsonResult audit(@RequestBody AuditVO auditVO) {
        articleService.audit(auditVO);
        return JsonResult.ok();
    }

    @ApiOperation("删除文章")
    @PreAuthorize("hasAuthority('blog:article:delete')")
    @OperationLog("删除文章")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable Long id){
        articleService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除文章")
    @PreAuthorize("hasAuthority('blog:article:delete')")
    @OperationLog("批量删除文章")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        articleService.removeList(idList);
        return JsonResult.ok();
    }
}
