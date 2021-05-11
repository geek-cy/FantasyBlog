package cn.fantasyblog.service;

import cn.fantasyblog.entity.Link;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.LinkQuery;

import java.util.List;

public interface LinkService {

    /**
     * 后台分页查询友链
     * @param current
     * @param size
     * @param linkQuery
     * @return
     */
    Page<Link> listTableByPage(Integer current, Integer size, LinkQuery linkQuery);

    /**
     * 前台分页查询友链
     * @param current
     * @param size
     * @return
     */
    Page<Link> listPreviewByPage(Integer current,Integer size);

    /**
     * 根据id删除友链
     * @param id
     */
    void remove(Long id);

    /**
     * 批量删除友链
     * @param listId
     */
    void removeList(List<Long> listId);

    /**
     * 添加或更新友链
     * @param link
     */
    void saveOrUpdate(Link link);

    /**
     * 审核友链
     */
    void audit(Link link);

    /**
     * 根据ID获取友链
     * @param id
     * @return
     */
    Link getId(Long id);
}
