package cn.myBlog.controller.admin;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.anntation.OperationLog;
import cn.myBlog.common.Constant;
import cn.myBlog.common.JsonResult;
import cn.myBlog.common.TableResult;
import cn.myBlog.entity.Category;
import cn.myBlog.query.CategoryQuery;
import cn.myBlog.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
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
 * @Date 2021-03-22 21:16
 */
@ApiModel("后台：分类管理")
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public JsonResult listAll() {
        return JsonResult.ok(categoryService.listAll());
    }

    @ApiOperation("查询分类")
    @PreAuthorize("hasAuthority('blog:category:query')")
    @AccessLog("查询分类")
    @GetMapping("/list")
    public TableResult listAll(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                               @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                               CategoryQuery categoryQuery){
        Page<Category> pageInfo = categoryService.listTableByCategory(page, limit, categoryQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getPages());
    }

    @ApiOperation("删除分类")
    @PreAuthorize("hasAuthority('blog:category:delete')")
    @OperationLog("删除分类")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id")Long id){
        categoryService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除分类")
    @PreAuthorize("hasAuthority('blog:category:delete')")
    @OperationLog("批量删除分类")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> idList){
        categoryService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("新增分类")
    @PreAuthorize("hasAuthority('blog:category:add')")
    @OperationLog("新增分类")
    @PostMapping
    public JsonResult save(@RequestBody Category category){
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        categoryService.saveOrUpdate(category);
        return JsonResult.ok();
    }

    @ApiOperation("编辑分类")
    @PreAuthorize("hasAuthority('blog:category:edit')")
    @OperationLog("编辑分类")
    @PutMapping
    public JsonResult update(@RequestBody Category category){
        category.setUpdateTime(new Date());
        categoryService.saveOrUpdate(category);
        return JsonResult.ok();
    }

    @ApiOperation("查询分类颜色")
    @AccessLog("查询分类颜色")
    @GetMapping("/colors")
    public JsonResult color(){
        return JsonResult.ok(categoryService.listColor());
    }

}
