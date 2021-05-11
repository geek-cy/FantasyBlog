package cn.fantasyblog.service;

import cn.fantasyblog.entity.AccessLog;
import cn.fantasyblog.vo.ViewDateVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.LogQuery;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;

public interface AccessLogService {
    /**
     * 保存日志数据
     */
    @Async
    void save(AccessLog log);

    /**
     * 分页获取所有日志
     * @param current 当前页码
     * @param size 页码大小
     * @param logQuery
     * @return 日志列表
     */
    Page<AccessLog> listByPage(Integer current, Integer size, LogQuery logQuery);

    /**
     * 根据ID删除日志
     * @param id 日志ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除日志
     * @param idList 日志ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 统计访问总数
     * @return 访问总数
     */
    Long countAll();

    /**
     * 查询最近访问日志
     * @return 访问日志列表
     */
    List<AccessLog> listNewest();

    /**
     * 统计最近7天的查询数
     * @return
     */
    List<ViewDateVO> countByLast7Days();



}
