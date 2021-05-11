package cn.myBlog.dao;

import cn.myBlog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserMapper {
    /**
     * 登录方法
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User login(@Param("username")String username,@Param("password")String password);
}
