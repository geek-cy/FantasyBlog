package cn.myBlog.dao;

import cn.myBlog.entity.Message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-18 22:12
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 前台分页查询所有留言
     */
    Page<Message> listPreviewByPage(IPage<Message> page);

    /**
     * 后台分页查询所有留言
     */
    Page<Message> listTableByPage(IPage<Message> page, @Param("ew") QueryWrapper<Message> wrapper);
}
