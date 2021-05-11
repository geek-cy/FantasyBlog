package cn.fantasyblog.utils;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.User;
import cn.fantasyblog.entity.Visitor;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-14 20:27
 */
public class UserInfoUtil {
    public static String getUsername() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getNickname();
    }

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
        Object o = RequestHolderUtil.getHttpServletRequest().getSession().getAttribute(Constant.USER);
        Long id = null;
        if (o != null) {
            Visitor visitor = (Visitor) o;
            id = visitor.getId();
            return id;
        } else id = 0L;
        return id;
    }
}
