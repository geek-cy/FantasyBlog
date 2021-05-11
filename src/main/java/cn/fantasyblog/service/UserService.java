package cn.fantasyblog.service;

import cn.fantasyblog.entity.User;
import cn.fantasyblog.vo.UserInfoVO;
import cn.fantasyblog.vo.UserLoginVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.VisitorQuery;

import java.util.List;

public interface UserService {
    /**
     * 后台分页查询所有用户
     */
    Page<User> listTableByPage(int current, int size, VisitorQuery userQuery);

    /**
     * 根据Id删除用户
     */
    void remove(Long id);

    /**
     * 批量删除用户
     */
    void removeList(List<Long> idList);

    /**
     * 添加用户
     */
    void saveOfUpdate(User user);

    /**
     * 用户改变状态
     */
    void changeStatus(User user);

    /**
     * 根据Id获取用户
     */
    User getById(Long id);

    /**
     * 用户数量
     */
    Long countAll();

    /**
     * 修改用户密码
     */
    void changePassword(UserLoginVO userLoginVO);

    /**
     * 修改个人信息
     *
     * @param userInfoVO 个人信息
     */
    void updateInfo(UserInfoVO userInfoVO);
}
