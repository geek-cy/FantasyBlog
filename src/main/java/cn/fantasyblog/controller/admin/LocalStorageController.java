package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.LocalStorage;
import cn.fantasyblog.query.FileQuery;
import cn.fantasyblog.service.LocalStorageService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Description 本地存储页面
 * @Author Cy
 * @Date 2021/5/12 22:06
 */
@Api(tags ="后台：本地存储管理")
@RestController
@RequestMapping("/admin/localStorage")
public class LocalStorageController {

    @Autowired
    private LocalStorageService localStorageService;
    
    @ApiOperation("查询本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:query')")
    @AccessLog("查询本地文件")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE) Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT) Integer limit,
                                       FileQuery fileQuery){
        Page<LocalStorage> pageInfo = localStorageService.listTableByPage(page, limit, fileQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("删除本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:delete')")
    @OperationLog("删除本地文件")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id") Long id){
        localStorageService.removeById(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:delete')")
    @OperationLog("批量删除本地文件")
    @DeleteMapping
    public JsonResult removeList(@NotNull @RequestBody List<Long> idList){
        localStorageService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("上传本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:add')")
    @OperationLog("上传本地文件")
    @PostMapping
    public JsonResult save(@RequestParam("file")MultipartFile[] file){
        localStorageService.save(file);
        return JsonResult.ok();
    }

    @ApiOperation("修改本地文件")
    @PreAuthorize("hasAuthority('sys:localstorage:edit')")
    @OperationLog("修改本地文件")
    @PutMapping
    public JsonResult update(@Validated @RequestBody LocalStorage localStorage){
        localStorage.setUpdateTime(new Date());
        localStorageService.update(localStorage);
        return JsonResult.ok();
    }
}
