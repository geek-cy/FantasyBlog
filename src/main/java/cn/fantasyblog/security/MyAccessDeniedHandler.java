package cn.fantasyblog.security;

import cn.fantasyblog.utils.AjaxUtil;
/*import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;*/
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 权限设置，异步请求
 * @Author Cy
 * @Date 2021-03-20 17:02
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setCharacterEncoding("utf-8");
        if (AjaxUtil.isAjaxRequest(httpServletRequest)) {
            httpServletResponse.sendError(403);
        } else if (!httpServletResponse.isCommitted()) {
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                    e.getMessage());
        }
    }
}
