package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.Tag;
import cn.fantasyblog.query.TagQuery;
import cn.fantasyblog.service.TagService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-22 21:22
 */
@Api(tags ="后台：标签管理")
@RestController
@RequestMapping("/admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("文章页面里查询所有标签")
    @AccessLog("文章页面里查询所有标签")
    @GetMapping
    public JsonResult listAll(){
        return JsonResult.ok(tagService.listAll());
    }

    @ApiOperation("后台分页查询所有标签")
    @PreAuthorize("hasAuthority('blog:tag:query')")
    @AccessLog("后台分页查询所有标签")
    @GetMapping("/list")
    public TableResult listTableByTag(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                                      @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                      TagQuery tagQuery){
        Page<Tag> pageInfo = tagService.listTableByTag(page, limit, tagQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("删除标签")
    @PreAuthorize("hasAuthority('blog:tag:delete')")
    @OperationLog("删除标签")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id")Long id){
        tagService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除标签")
    @PreAuthorize("hasAuthority('blog:tag:delete')")
    @OperationLog("批量删除标签")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        tagService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("编辑标签")
    @PreAuthorize("hasAuthority('blog:tag:edit')")
    @OperationLog("编辑标签")
    @PutMapping
    public JsonResult update(@RequestBody Tag tag){
        tag.setUpdateTime(new Date());
        tagService.saveOrUpdate(tag);
        return JsonResult.ok();
    }

    @ApiOperation("新增标签")
    @PreAuthorize("hasAuthority('blog:tag:add')")
    @OperationLog("新增标签")
    @PostMapping
    public JsonResult save(@RequestBody Tag tag){
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        tagService.saveOrUpdate(tag);
        return JsonResult.ok();
    }

    @ApiOperation("查询标签颜色")
    @AccessLog("查询标签颜色")
    @GetMapping("/colors")
    public JsonResult color(){
        return JsonResult.ok(tagService.color());
    }
}
