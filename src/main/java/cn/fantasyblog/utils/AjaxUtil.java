package cn.fantasyblog.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 判断请求是否异步
 * @Author Cy
 * @Date 2021-03-20 17:05
 */
public class AjaxUtil {
    public static final String AJAX_REQUEST = "XMLHttpRequest";

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        //如果requestType能拿到值，并且值为 XMLHttpRequest ,表示客户端的请求为异步请求，那自然是ajax请求了，反之如果为null,则是普通的请求
        if (!StringUtils.isEmpty(requestType) && AJAX_REQUEST.equals(requestType)) {
            return true;
        }
        return false;
    }
}
