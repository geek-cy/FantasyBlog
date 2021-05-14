package cn.fantasyblog.utils;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.User;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.vo.VisitorVO;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-14 20:27
 */
public class UserInfoUtil {
    public static Long getUserId() {
        /*Object o = RequestHolder.getHttpServletRequest().getSession().getAttribute(Constant.USER);
        Long id = null;
        if(o != null){
            User user = (User) o;
            id = user.getId();
        } else {
            throw new BadRequestException("用户未登录");
        }
        return id;*/
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    public static Long getVisitorId() {
        HttpSession session = RequestHolderUtil.getHttpServletRequest().getSession();
        Object attribute = RequestHolderUtil.getHttpServletRequest().getSession().getAttribute(Constant.VISITOR);
        if (attribute == null) throw new BadRequestException("您未登录请先登录");
        return (Long) attribute;
    }
}
