package cn.fantasyblog.service;

import cn.fantasyblog.entity.OperationLog;
import cn.fantasyblog.vo.ViewDateVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.LogQuery;

import java.util.List;

public interface OperationLogService {

    /**
     * 查询最近操作日志
     * @return
     */
    List<OperationLog> listNewest();

    /**
     * 查询最近7天操作数
     * @return
     */
    List<ViewDateVO> countByLast7Days();

    /**
     * 新增操作日志
     */
    void save(OperationLog operationLog);

    /**
     * 后台查询操作日志
     */
    List<OperationLog> listTableByPage(Integer current, Integer size, LogQuery logQuery);

    /**
     * 删除操作日志
     */
    void remove(Long id);

    /**
     * 批量删除操作日志
     */
    void removeList(List<Long> idList);

    /**
     * 统计访问总数
     */
    Long countAll();

}
