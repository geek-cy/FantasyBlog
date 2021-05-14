package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.service.VisitorService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.VisitorQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-03 20:07
 */
@Api(tags ="后台：查询访客")
@RestController
@RequestMapping("/admin/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @ApiOperation("后台分页查询访客")
    @PreAuthorize("hasAuthority('sys:visitor:query')")
    @AccessLog("后台查询访客")
    @GetMapping
    public TableResult listByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE) Integer page,
                                  @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT) Integer limit,
                                  VisitorQuery visitorQuery) {
        Page<Visitor> pageInfo = visitorService.listTableByPage(page, limit, visitorQuery);
        return TableResult.tableOk(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @ApiOperation("删除访客")
    @PreAuthorize("hasAuthority('sys:visitor:delete')")
    @OperationLog("删除访客")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id") Long id) {
        visitorService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除访客")
    @PreAuthorize("hasAuthority('sys:visitor:delete')")
    @OperationLog("批量删除访客")
    @DeleteMapping
    public JsonResult removeList(@NotEmpty @RequestBody List<Long> idList){
        visitorService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("修改访客状态")
    @PreAuthorize("hasAuthority('sys:visitor:status')")
    @OperationLog("修改访客状态")
    @PutMapping("/status")
    public JsonResult changeStatus(@RequestBody Visitor visitor){
        visitorService.changeStatus(visitor);
        return JsonResult.ok();
    }
}
