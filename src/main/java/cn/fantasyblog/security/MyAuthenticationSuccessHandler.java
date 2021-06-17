
package cn.fantasyblog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @param request 用于服务端跳转
     * @param response 用于客户端跳转或返回Json数据
     * @param authentication 登录成功的用户信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        log.info("登录成功");
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

