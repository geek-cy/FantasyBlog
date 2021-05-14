package cn.fantasyblog.controller.front;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.service.VisitorService;
import cn.fantasyblog.utils.MD5Util;
import cn.fantasyblog.vo.VisitorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation("访客注册")
    @AccessLog("访客注册")
    @PostMapping
    public ResponseEntity<Object> register(@Validated @RequestBody Visitor visitor){
        visitor.setPassword(MD5Util.code(visitor.getPassword()));
        visitor.setAvatar(Constant.DEFAULT_AVATAR);
        visitor.setCreateTime(new Date());
        visitor.setUpdateTime(visitor.getCreateTime());
        visitor.setStatus(Constant.VISITOR_ENABLE);
        visitorService.save(visitor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("访客登录")
    @AccessLog("访客登录")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody VisitorVO visitorVO, HttpServletRequest request){
        visitorVO.setPassword(MD5Util.code(visitorVO.getPassword()));
        Visitor visitor = visitorService.login(visitorVO);
        request.getSession().setAttribute("visitor",visitor.getId());
        return new ResponseEntity<>(visitor,HttpStatus.OK);
    }
}
