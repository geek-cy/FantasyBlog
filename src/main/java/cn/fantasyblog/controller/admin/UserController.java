package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.common.TableResult;
import cn.fantasyblog.entity.User;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.UserService;
import cn.fantasyblog.vo.UserInfoVO;
import cn.fantasyblog.vo.UserLoginVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.VisitorQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-04 21:00
 */
@Api(tags ="后台：用户管理")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @ApiOperation("后台查询所有用户")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @AccessLog("后台查询所有用户")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer limit,
                                       VisitorQuery userQuery){
        Page<User> pageInfo = userService.listTableByPage(page, limit, userQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @OperationLog("删除用户")
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        userService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("批量删除用户")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @OperationLog("批量删除用户")
    @DeleteMapping
    public JsonResult deleteList(@RequestBody List<Long> idList){
        userService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("改变用户状态")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @OperationLog("改变用户状态")
    @PutMapping("/status")
    public JsonResult changeStatus(@RequestBody User user){
        userService.changeStatus(user);
        return JsonResult.ok();
    }

    @ApiOperation("新增用户")
  @PreAuthorize("hasAuthority('sys:user:add')")
    @OperationLog("新增用户")
    @PostMapping
    public JsonResult insert(@Validated @RequestBody User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(Constant.DEFAULT_PASSWORD));
        user.setAvatar(Constant.DEFAULT_AVATAR);
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        userService.saveOfUpdate(user);
        return JsonResult.ok();
    }

    @ApiOperation("更新用户")
    @OperationLog("更新用户")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @PutMapping
    public JsonResult update(@Validated @RequestBody User user){
        user.setUpdateTime(new Date());
        userService.saveOfUpdate(user);
        return JsonResult.ok();
    }

    @ApiOperation("修改密码")
    @OperationLog("修改密码")
    @PutMapping("/password")
    public JsonResult changePassword(@Validated @RequestBody UserLoginVO userLoginVO){
        userService.changePassword(userLoginVO);
        return JsonResult.ok();
    }

    @ApiOperation("查询个人信息")
    @GetMapping("/{id}/info")
    public JsonResult getInfo(@PathVariable("id") Long id){
        return JsonResult.ok(userService.getById(id));
    }

    @ApiOperation("更新个人信息")
    @OperationLog("更新个人信息")
    @PutMapping("/info")
    public JsonResult updateInfo(@Validated @RequestBody UserInfoVO userInfoVO){
        userService.updateInfo(userInfoVO);
        return JsonResult.ok();
    }
}
