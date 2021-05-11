package cn.fantasyblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fantasyblog.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-25 22:54
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 批量添加菜单角色
     */
    void insertBatch(@Param("menuId") Long menuId, @Param("roleIdList") List<Long> roleList);

}
