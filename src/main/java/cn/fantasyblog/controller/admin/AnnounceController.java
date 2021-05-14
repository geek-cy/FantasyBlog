package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.Announce;
import cn.fantasyblog.query.AnnounceQuery;
import cn.fantasyblog.service.AnnounceService;
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
 * @Date 2021-04-22 21:30
 */
@Api(tags ="后台：公告管理")
@RestController
@RequestMapping("admin/announce")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;

    @ApiOperation("后台分页查询公告")
    @PreAuthorize("hasAuthority('sys:announce:query')")
    @AccessLog("后台分页查询公告")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                       AnnounceQuery announceQuery){
        Page<Announce> pageInfo = announceService.listTableByPage(page, limit, announceQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("后台新增公告")
    @OperationLog("后台新增公告")
    @PreAuthorize("hasAuthority('sys:announce:add')")
    @PostMapping
    public JsonResult add(@Validated @RequestBody Announce announce){
        announce.setCreateTime(new Date());
        announce.setUpdateTime(announce.getCreateTime());
        announceService.saveOrUpdate(announce);
        return JsonResult.ok();
    }

    @ApiOperation("后台编辑公告")
    @OperationLog("后台编辑公告")
    @PreAuthorize("hasAuthority('sys:announce:edit')")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Announce announce){
        announce.setUpdateTime(new Date());
        announceService.saveOrUpdate(announce);
        return JsonResult.ok();
    }

    @ApiOperation("后台删除公告")
    @OperationLog("后台删除公告")
    @PreAuthorize("hasAuthority('sys:announce:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id") Long id){
        announceService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("后台批量删除公告")
    @OperationLog("后台批量删除公告")
    @PreAuthorize("hasAuthority('sys:announce:delete')")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        announceService.removeList(idList);
        return JsonResult.ok();
    }
}
