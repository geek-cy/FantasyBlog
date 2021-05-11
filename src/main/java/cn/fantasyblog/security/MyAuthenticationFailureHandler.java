package cn.myBlog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Description 现自定义的用户失败登陆处理
 * @Author Cy
 * @Date 2021-03-20 16:52
 */

@Component
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        log.info("登录失败");
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","*");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        //具体错误
        HashMap<String, Object> map = new HashMap<>();
        if(e instanceof UsernameNotFoundException){
            map.put("message","用户名不存在");
        } else if(e instanceof BadCredentialsException){
            map.put("message","密码错误");
        }else if(e instanceof DisabledException){
            map.put("message","账号已停用");
        } else if(e instanceof LockedException){
            map.put("message","账号已锁定，请联系管理员");
        }
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
