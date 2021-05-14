package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.Link;
import cn.fantasyblog.service.LinkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.LinkQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-11 20:40
 */
@Api(tags ="后台：友链管理")
@RestController
@RequestMapping("/admin/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @ApiOperation("后台分页查询友链")
    @AccessLog("后台分页查询友链")
    @PreAuthorize("hasAuthority('blog:link:query')")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE) Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                       LinkQuery linkQuery){
        Page<Link> pageInfo = linkService.listTableByPage(page, limit, linkQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("删除友链")
    @OperationLog("删除友链")
    @PreAuthorize("hasAuthority('blog:link:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id")Long id){
        linkService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除友链")
    @OperationLog("批量删除友链")
    @PreAuthorize("hasAuthority('blog:link:delete')")
    @DeleteMapping
    public JsonResult removeList(@NotEmpty @RequestBody List<Long> idList){
        linkService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("增加友链")
    @OperationLog("增加友链")
    @PreAuthorize("hasAuthority('blog:link:add')")
    @PostMapping
    public JsonResult add(@Validated @RequestBody Link link){
        link.setCreateTime(new Date());
        link.setUpdateTime(link.getCreateTime());
        linkService.saveOrUpdate(link);
        return JsonResult.ok();
    }

    @ApiOperation("更新友链")
    @OperationLog("更新友链")
    @PreAuthorize("hasAuthority('blog:link:edit')")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Link link){
        link.setUpdateTime(new Date());
        linkService.saveOrUpdate(link);
        return JsonResult.ok();
    }

    @ApiOperation("审核友链")
    @OperationLog("审核友链")
    @PreAuthorize("hasAuthority('blog:link:audit')")
    @PutMapping("/audit")
    public JsonResult audit(@RequestBody Link link){
        linkService.audit(link);
        return JsonResult.ok();
    }
}
