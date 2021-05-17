package cn.fantasyblog.service;

import cn.fantasyblog.entity.Notice;
import cn.fantasyblog.query.NoticeQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface NoticeService {

    /**
     * 后台分页查询公告
     */
    Page<Notice> listTableByPage(Integer current, Integer size, NoticeQuery noticeQuery);

    /**
     * 新增或更新公告
     */
    void saveOrUpdate(Notice notice);

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
    void audit(Notice notice);

    /**
     * 显示最近公告
     */
    List<Notice> listNewest();

    /**
     * 根据Id获取公告
    */
    Notice getById(Long id);
}
