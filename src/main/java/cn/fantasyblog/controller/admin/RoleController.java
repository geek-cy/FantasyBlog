package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.Role;
import cn.fantasyblog.query.RoleQuery;
import cn.fantasyblog.service.RoleService;
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
 * @Date 2021-04-07 11:02
 */
@Api(tags ="后台：角色管理")
@RestController
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation("后台查询角色")
    @AccessLog("后台查询角色")
    @PreAuthorize("hasAuthority('sys:role:query')")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE) Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT) Integer limit,
                                       RoleQuery roleQuery){
        Page<Role> pageInfo = roleService.listTableByPage(page, limit, roleQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("查询所有角色")
    @AccessLog("查询所有角色")
    @GetMapping("/list")
    public JsonResult listAll(){
        return JsonResult.ok(roleService.listAll());
    }

    @ApiOperation("删除角色")
    @OperationLog("删除角色")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id") Long id){
        roleService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除角色")
    @OperationLog("批量删除角色")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping
    public JsonResult removeList(@RequestBody List<Long> list){
        roleService.removeList(list);
        return JsonResult.ok();
    }

    @ApiOperation("添加角色")
    @OperationLog("添加角色")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @PostMapping
    public JsonResult add(@RequestBody Role role){
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        roleService.saveOrUpdate(role);
        return JsonResult.ok();
    }

    @ApiOperation("更新角色")
    @OperationLog("更新角色")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @PutMapping
    public JsonResult update(@RequestBody Role role){
        role.setUpdateTime(new Date());
        roleService.saveOrUpdate(role);
        return JsonResult.ok();
    }
}
