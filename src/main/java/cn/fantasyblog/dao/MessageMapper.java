package cn.fantasyblog.dao;

import cn.fantasyblog.entity.Message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-18 22:12
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 分页查询所有顶级留言
     */
    Page<Message> listRootByPage(IPage<Message> page);

    /**
     * 查询所有留言,子留言中包含父留言的nickname
     */
    List<Message> listAll();

    /**
     * 更新留言顺序
     */
    void updateID();

}
