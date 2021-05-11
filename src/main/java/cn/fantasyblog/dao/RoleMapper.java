package cn.myBlog.dao;

import cn.myBlog.entity.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-07 11:03
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    Page<Role> listTableByPage(IPage<Role> page, @Param("ew") QueryWrapper<Role> wrapper);

}
