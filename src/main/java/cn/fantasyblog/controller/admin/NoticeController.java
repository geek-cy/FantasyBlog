package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.Notice;
import cn.fantasyblog.query.NoticeQuery;
import cn.fantasyblog.service.NoticeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/14 16:54
 */
@Api(tags = "后台：通知管理")
@RestController
@RequestMapping("/admin/notice")
public class NoticeController {

    private NoticeService noticeService;

    @ApiOperation("后台分页查询通知")
    @PreAuthorize("hasAuthority('blog:notice:query')")
    @AccessLog("后台分页查询通知")
    @GetMapping
    public TableResult listTableByPage(@RequestParam("page") Integer page,
                                       @RequestParam("limit") Integer limit,
                                       NoticeQuery noticeQuery) {
        Page<Notice> pageInfo = noticeService.listTableByPage(page, limit, noticeQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("删除通知")
    @PreAuthorize("hasAuthority('blog:notice:delete')")
    @OperationLog("删除通知")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id") Long id){
        noticeService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除通知")
    @PreAuthorize("hasAuthority('blog:notice:delete')")
    @OperationLog("批量删除通知")
    @DeleteMapping
    public JsonResult removeList(List<Long> idList){
        noticeService.removeList(idList);
        return JsonResult.ok();
    }
}
