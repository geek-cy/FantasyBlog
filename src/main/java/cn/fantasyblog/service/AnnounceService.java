package cn.fantasyblog.service;

import cn.fantasyblog.entity.Announce;
import cn.fantasyblog.query.AnnounceQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

public interface AnnounceService {

    /**
     * 后台分页查询公告
     */
    Page<Announce> listTableByPage(Integer current, Integer size, AnnounceQuery announceQuery);

    /**
     * 新增或更新公告
     */
    void saveOrUpdate(Announce announce);

    /**
     * 删除公告
     */
    void remove(Long id);

    /**
     * 批量删除公告
     */
    void removeList(List<Long> idList);

    /**
     * 是否显示公告
     */
    void audit(Announce announce);

    /**
     * 显示最近公告
     */
    List<Announce> listNewest();

    /**
     * 根据Id获取公告
    */
    Announce getById(Long id);
}
