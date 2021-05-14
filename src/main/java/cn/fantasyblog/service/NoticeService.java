package cn.fantasyblog.service;

import cn.fantasyblog.entity.Notice;
import cn.fantasyblog.query.NoticeQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/14 16:58
 */
public interface NoticeService {

    /**
     * 后台分页查询通知
     */
    Page<Notice> listTableByPage(Integer current, Integer size, NoticeQuery noticeQuery);

    /**
     * 删除通知
     */
    void remove(Long id);

    /**
     * 批量删除通知
     */
    void removeList(List<Long> idList);

}
