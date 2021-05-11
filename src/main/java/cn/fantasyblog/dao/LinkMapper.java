package cn.myBlog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.entity.Link;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkMapper extends BaseMapper<Link> {

    /**
     * 后台分页查询友链
     * @param page
     * @param wrapper
     * @return
     */
    Page<Link> listTableByPage(IPage<Link> page, @Param("ew")QueryWrapper<Link> wrapper);

}
