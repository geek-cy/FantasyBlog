package cn.myBlog.controller.admin;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.common.Constant;
import cn.myBlog.common.JsonResult;
import cn.myBlog.common.TableResult;
import cn.myBlog.entity.OperationLog;
import cn.myBlog.query.LogQuery;
import cn.myBlog.service.OperationLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-22 16:53
 */
@Api("后台：操作日志管理")
@RestController
@RequestMapping("/admin/operation-log")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation("后台分页查询操作日志")
    @AccessLog("后台分页查询操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:query')")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                       LogQuery logQuery){
        Page<OperationLog> pageInfo = operationLogService.listTableByPage(page, limit, logQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("删除操作日志")
    @cn.myBlog.anntation.OperationLog("删除操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id")Long id){
        operationLogService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除操作日志")
    @cn.myBlog.anntation.OperationLog("批量删除操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        operationLogService.removeList(idList);
        return JsonResult.ok();
    }


}
