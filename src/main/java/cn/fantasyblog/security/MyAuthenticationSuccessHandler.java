
package cn.fantasyblog.security;

import cn.fantasyblog.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * request.getParameter() 和request.getAttribute() 区别
 * 1.都是HttpServletRequest类中的方法
 * 2.都是用来传递数据用的
 * 不同:1、getParameter():响应的是两个web组件之间为链接(重定向)关系时，
 * 如get和post表单提交请求，传递请求参数，注意此种方法是从web客户端向web服务端传递数据,代表HTTP请求数据
 * 2、getAttribute()：响应的两个web组件之间为转发关系时，服务端的转发源通过setAttribute()设置传递的参数，
 * 然后转发目的通过setAttribute()获取传递的参数，这样转发时数据就不会丢失，注意此种方法只存在于web容器内部
 */

/**
 * @Description 实现自定义的用户成功登陆处理
 * @Author Cy
 * @Date 2021-03-18 19:52
 */


@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

/**
     * ObjectMapper是Jackson库中主要用于读取和写入Json数据的类，
     * 能够很方便地将Java对象转为Json格式的数据，
     * 用于后端Servlet向AJAX传递Json数据，动态地将数据展示在页面上
     */

    @Autowired
    private ObjectMapper objectMapper;

/**
     *
     * @param request
     * @param response
     * @param authentication 描述当前用户的相关信息
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        log.info("登录成功");
        String userId = null;
        // 取到代表当前用户的信息
        Object principal = authentication.getPrincipal();
        if(principal != null){
            User user = (User) principal;
            request.getSession().setAttribute("user",user);
            userId = user.getId().toString();
        }
        String parameter = request.getParameter("remember-me");
        //String类型转boolean类型的一个方法
        boolean rememberMe = Boolean.parseBoolean(parameter);
        if(rememberMe){
            Cookie cookie = new Cookie("userId", userId);
            cookie.setMaxAge(14*24*60*60);
            response.addCookie(cookie);
        }
        // 覆盖Spring Security设置的标头。 确保CSS，JavaScript和图像等正确缓存
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        // 把Java对象转为Json格式的数据响应到页面中,返回String对象,和writeValue等价
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
        log.info("{}",authentication.getDetails().toString());
        log.info("{}",authentication.toString());
        log.info("{}",authentication.getPrincipal().toString());
    }
}

