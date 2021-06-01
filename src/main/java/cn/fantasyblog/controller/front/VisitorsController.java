package cn.fantasyblog.controller.front;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.service.VisitorService;
import cn.fantasyblog.utils.MD5Util;
import cn.fantasyblog.utils.UserInfoUtil;
import cn.fantasyblog.vo.VisitorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-02 20:41
 */
@Api(tags ="前台：访客功能")
@Controller
@RequestMapping("/visitor")
public class VisitorsController {

    @Autowired
    VisitorService visitorService;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @ApiOperation("访客注册")
    @OperationLog("访客注册")
    @PostMapping
    public ResponseEntity<Object> register(@Validated @RequestBody Visitor visitor){
        visitor.setPassword(MD5Util.code(visitor.getPassword()));
        visitor.setAvatar(Constant.DEFAULT_AVATAR);
        visitor.setCreateTime(new Date());
        visitor.setUpdateTime(visitor.getCreateTime());
        visitor.setStatus(Constant.VISITOR_DISABLE);
        visitorService.save(visitor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("访客登录")
    @AccessLog("访客登录")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody VisitorVO visitorVO, HttpServletRequest request){
        visitorVO.setPassword(MD5Util.code(visitorVO.getPassword()));
        Visitor visitor = visitorService.login(visitorVO);
        HttpSession session=request.getSession();
        session.setAttribute(Constant.VISITOR_ID,visitor.getId());
        session.setAttribute(Constant.VISITOR_NAME,visitor.getUsername());
        return new ResponseEntity<>(visitor,HttpStatus.OK);
    }

    @ApiOperation("访客激活")
    @OperationLog("访客激活")
    @ResponseBody
    @GetMapping("/activation/{id}/{code}")
    public String activation(@PathVariable("id") Long id, @PathVariable("code")String code){
        return visitorService.activation(id, code);
    }
}
