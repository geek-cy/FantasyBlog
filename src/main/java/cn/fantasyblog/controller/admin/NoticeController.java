package cn.myBlog.controller.admin;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.anntation.OperationLog;
import cn.myBlog.common.Constant;
import cn.myBlog.common.JsonResult;
import cn.myBlog.common.TableResult;
import cn.myBlog.entity.Notice;
import cn.myBlog.query.NoticeQuery;
import cn.myBlog.service.NoticeService;
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
@Api("后台：公告管理")
@RestController
@RequestMapping("admin/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation("后台分页查询公告")
    @PreAuthorize("hasAuthority('sys:notice:query')")
    @AccessLog("后台分页查询公告")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                       NoticeQuery noticeQuery){
        Page<Notice> pageInfo = noticeService.listTableByPage(page, limit, noticeQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("后台新增公告")
    @OperationLog("后台新增公告")
    @PreAuthorize("hasAuthority('sys:notice:add')")
    @PostMapping
    public JsonResult add(@Validated @RequestBody Notice notice){
        notice.setCreateTime(new Date());
        notice.setUpdateTime(notice.getCreateTime());
        noticeService.saveOrUpdate(notice);
        return JsonResult.ok();
    }

    @ApiOperation("后台编辑公告")
    @OperationLog("后台编辑公告")
    @PreAuthorize("hasAuthority('sys:notice:edit')")
    @PutMapping
    public JsonResult update(@Validated @RequestBody Notice notice){
        notice.setUpdateTime(new Date());
        noticeService.saveOrUpdate(notice);
        return JsonResult.ok();
    }

    @ApiOperation("后台删除公告")
    @OperationLog("后台删除公告")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id") Long id){
        noticeService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("后台批量删除公告")
    @OperationLog("后台批量删除公告")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        noticeService.removeList(idList);
        return JsonResult.ok();
    }
}
