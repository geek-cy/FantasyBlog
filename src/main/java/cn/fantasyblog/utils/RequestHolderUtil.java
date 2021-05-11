package cn.myBlog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description 代码获取HttpServletRequest
 * @Author Cy
 * @Date 2021-03-14 20:29
 */
public class RequestHolderUtil {
    public static HttpServletRequest getHttpServletRequest(){
       return ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
