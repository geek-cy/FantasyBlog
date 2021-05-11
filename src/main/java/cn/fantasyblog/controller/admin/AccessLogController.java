package cn.myBlog.controller.admin;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.anntation.OperationLog;
import cn.myBlog.common.Constant;
import cn.myBlog.common.JsonResult;
import cn.myBlog.common.TableResult;
import cn.myBlog.service.AccessLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.query.LogQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-14 21:36
 */
@Api(tags = "后台：访问日志管理")
@RestController
@RequestMapping("/admin/access-log")
public class AccessLogController {
    @Autowired
    private AccessLogService accessLogService;

    @ApiOperation("查询访问日志")
    @PreAuthorize("hasAuthority('sys:accesslog:query')")
    @AccessLog("查询访问日志")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE) Integer page,
                                  @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                  LogQuery logQuery){
        Page<cn.myBlog.entity.AccessLog> pageInfo = accessLogService.listByPage(page, limit, logQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("删除访问日志")
    @PreAuthorize("hasAuthority('sys:accesslog:delete')")
    @OperationLog("删除访问日志")
    @DeleteMapping("/{id}")
    public JsonResult removeById(@NotNull @PathVariable("id") Long id){
        accessLogService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除访问日志")
    @PreAuthorize("hasAuthority('sys:accesslog:delete')")
    @OperationLog("批量删除访问日志")
    @DeleteMapping
    public JsonResult removeBatch(@NotEmpty @RequestBody List<Long> idList){
        accessLogService.removeByIdList(idList);
        return JsonResult.ok();
    }
}
