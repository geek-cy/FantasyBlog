package cn.fantasyblog.service;


import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.vo.VisitorVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.VisitorQuery;

import java.util.List;

public interface VisitorService {

    /**
     * 前台注册访客
     */
    void save(Visitor visitor);

    /**
     * 前台登录访客
     */
    Visitor login(VisitorVO visitorVO);

    /**
     * 后台分页查询访客
     */
    Page<Visitor> listTableByPage(Integer current, Integer size, VisitorQuery visitorQuery);

    /**
     * 根据ID删除访客
     */
    void remove(Long id);

    /**
     * 批量删除访客
     */
    void removeList(List<Long> idList);

    /**
     * 根据Id改变访客状态
     * @param
     */
    void changeStatus(Visitor visitor);

    /**
     * 访客数量
     */
    Long countAll();

    /**
     * 访客激活
     */
    String activation(Long id,String code);

    /**
     * 删除未激活访客
     */
    void removeVisitors();

}
