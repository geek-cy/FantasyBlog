package cn.myBlog.controller.front;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.anntation.OperationLog;
import cn.myBlog.common.Constant;
import cn.myBlog.common.JsonResult;
import cn.myBlog.entity.Message;
import cn.myBlog.service.MessageService;
import cn.myBlog.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-18 21:22
 */
@Api("前台：留言管理")
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    MessageService messageService;

    @ApiOperation("前台查询留言")
    @AccessLog("前台查询留言")
    @GetMapping
    public ResponseEntity<Object> listPreviewByPage(@RequestParam(value = "current",defaultValue = Constant.PAGE)Integer current,
                                                    @RequestParam(value = "size",defaultValue = Constant.PAGE_SIZE)Integer size){
        Page<Message> pageInfo = messageService.listPreviewByPage(current, size);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

    @ApiOperation("前台新增留言")
    @OperationLog("前台新增留言")
    @PostMapping
    public JsonResult saveMessage(@Validated @RequestBody Message message, HttpServletRequest request){
        message.setCreateTime(new Date());
        message.setBrowser(StringUtils.getBrowser(request));
        message.setOs(StringUtils.getClientOS(request));
        message.setRequestIp(StringUtils.getIp(request));
        message.setAddress(StringUtils.getCityInfo(message.getRequestIp()));
        message.setStatus(Constant.AUDIT_WAIT);
        messageService.save(message);
        return JsonResult.ok();
    }
}
