package cn.fantasyblog.security;

//import com.blog.cy.fantasyblog.exception.ValidateCodeException;
import cn.fantasyblog.exception.ValidateCodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


/**
 * ObjectMapper是Jackson库中主要用于读取和写入Json数据的类，
 * 能够很方便地将Java对象转为Json格式的数据，
 * 用于后端Servlet向AJAX传递Json数据，动态地将数据展示在页面上。
 */

/**
 * @Description Spring的OncePerRequestFilter类实际上是一个实现了Filter接口的抽象类。
 * spring对Filter进行了一些封装处理。
 * OncePerRequestFilter是在一次外部请求中只过滤一次。
 * 对于服务器内部之间的forward等请求，不会再次执行过滤方法。
 * 实现Filter接口，也会在一次请求中只过滤一次, 实际上，OncePerRequestFilter是为了兼容不同的web容器，
 * 也就是说其实不是所有的容器都过滤一次。Servlet版本不同，执行的过程也不同。
 * @Author Cy
 * @Date 2021-03-18 16:19
 */


@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.pathEquals("/admin/login",httpServletRequest.getRequestURI())){
            // 框里拿出用户输入的验证码
            String verCode =httpServletRequest.getParameter("verCode");
            if (StringUtils.isEmpty(verCode)) {
                throw new ValidateCodeException("验证码不能为空！");
            }
            if(!CaptchaUtil.ver(verCode,httpServletRequest)){
                throw new ValidateCodeException("验证码不匹配");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

