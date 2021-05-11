package cn.fantasyblog.controller.admin;


import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 后台安全控制
 * @Author Cy
 * @Date 2021-03-17 22:57
 */
@Api(tags = "后台：安全控制")
@Controller
@RequestMapping("/admin")
public class SecurityController {

    @ApiOperation("登录页面")
    @GetMapping({"/","/login.html"})
    public String loginPage(){
        return "admin/home/login";
    }

    @ApiOperation("403页面")
    @ExceptionHandler({AccessDeniedException.class})
    @GetMapping(value = "/403.html")
    public String noPermission(){
        return "error/403";
    }

    @ApiOperation("验证码")
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaUtil.out(request,response);
    }
}
