package cn.fantasyblog.service;

import cn.fantasyblog.entity.Role;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.RoleQuery;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-07 11:03
 */
public interface RoleService {

    /**
     * 后台查询所有角色
     * @param current
     * @param size
     * @param roleQuery
     * @return
     */
    Page<Role> listTableByPage(Integer current, Integer size, RoleQuery roleQuery);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> listAll();

    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    void remove(Long id);

    /**
     * 删除文章
     * @param idList
     */
    void removeList(List<Long> idList);

    /**
     * 添加或更新角色
     * @param role
     */
    void saveOrUpdate(Role role);

    /**
     * 根据id获取角色
     */
    Role getById(Long id);
}
