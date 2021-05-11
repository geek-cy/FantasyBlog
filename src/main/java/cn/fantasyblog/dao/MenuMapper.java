package cn.fantasyblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fantasyblog.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-18 23:31
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户ID查询菜单
     * @param userId 用户ID
     * @return
     */
    List<Menu> listMenuByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    List<Menu> listPermissionByUserId(@Param("userId") Long userId);
}
