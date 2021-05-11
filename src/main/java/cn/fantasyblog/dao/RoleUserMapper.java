package cn.fantasyblog.dao;

import cn.fantasyblog.entity.RoleUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-07 22:54
 */
@Repository
public interface RoleUserMapper extends BaseMapper<RoleUser> {

    /**
     * 根据用户id更新角色id
     * @param rid 角色id
     * @param uid 用户id
     */
    void updateRoleId(@Param("rid") Long rid,@Param("uid") Long uid);
}
