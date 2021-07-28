package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.OperationLog;
import cn.fantasyblog.query.LogQuery;
import cn.fantasyblog.service.OperationLogService;
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
@Api(tags ="后台：操作日志管理")
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
        List<cn.fantasyblog.entity.OperationLog> accessLogs = operationLogService.listTableByPage((page - 1) * limit, limit, logQuery);
        return TableResult.tableOk(accessLogs, operationLogService.countAll());
    }

    @ApiOperation("删除操作日志")
    @cn.fantasyblog.anntation.OperationLog("删除操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id")Long id){
        operationLogService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除操作日志")
    @cn.fantasyblog.anntation.OperationLog("批量删除操作日志")
    @PreAuthorize("hasAuthority('sys:operationlog:delete')")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        operationLogService.removeList(idList);
        return JsonResult.ok();
    }


}
