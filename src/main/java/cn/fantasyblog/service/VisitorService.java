package cn.fantasyblog.service;


import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.vo.VisitorVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.VisitorQuery;

import java.util.List;

public interface VisitorService {

    /**
     * 前台注册访客
     * @param visitor
     */
    void save(Visitor visitor);

    /**
     * 前台登录访客
     * @param visitorVO
     */
    Visitor login(VisitorVO visitorVO);

    /**
     * 后台分页查询访客
     * @param current
     * @param size
     * @param visitorQuery
     * @return
     */
    Page<Visitor> listTableByPage(Integer current, Integer size, VisitorQuery visitorQuery);

    /**
     * 根据ID删除访客
     * @param id
     */
    void remove(Long id);

    /**
     * 批量删除访客
     * @param idList
     */
    void removeList(List<Long> idList);

    /**
     * 根据Id改变访客状态
     * @param
     */
    void changeStatus(Visitor visitor);

    /**
     * 访客数量
     * @return
     */
    Long countAll();
}
