package cn.myBlog.controller.admin;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.anntation.OperationLog;
import cn.myBlog.common.Constant;
import cn.myBlog.common.JsonResult;
import cn.myBlog.common.TableResult;
import cn.myBlog.entity.Photo;
import cn.myBlog.query.PhotoQuery;
import cn.myBlog.service.PhotoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-14 16:11
 */
@Api("后台：相册管理")
@RestController
@RequestMapping("/admin/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @ApiOperation("后台分页查询相册")
    @AccessLog("后台分页查询相册")
    @PreAuthorize("hasAuthority('blog:photo:query')")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                       PhotoQuery photoQuery){
        Page<Photo> pageInfo = photoService.listTableByPage(page, limit, photoQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getPages());
    }

    @ApiOperation("后台删除相册")
    @OperationLog("后台删除相册")
    @PreAuthorize("hasAuthority('blog:photo:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id")Long id){
        photoService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("后台批量删除相册")
    @OperationLog("后台批量删除相册")
    @PreAuthorize("hasAuthority('blog:photo:delete')")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        photoService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("添加相册")
    @OperationLog("添加相册")
    @PreAuthorize("hasAuthority('blog:photo:add')")
    @PostMapping
    public JsonResult save(@RequestBody Photo photo){
        photo.setCreateTime(new Date());
        photo.setUpdateTime(photo.getCreateTime());
        photoService.saveOrUpdate(photo);
        return JsonResult.ok();
    }

    @ApiOperation("更新相册")
    @OperationLog("更新相册")
    @PreAuthorize("hasAuthority('blog:photo:edit')")
    @PutMapping
    public JsonResult update(@RequestBody Photo photo){
        photo.setUpdateTime(new Date());
        photoService.saveOrUpdate(photo);
        return JsonResult.ok();
    }
}
