package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.TableConstant;
import cn.fantasyblog.entity.AccessLog;
import cn.fantasyblog.entity.OperationLog;
import cn.fantasyblog.service.OperationLogService;
import cn.fantasyblog.vo.ViewDateVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.dao.OperationLogMapper;
import cn.fantasyblog.query.LogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-20 15:27
 */
@Service
@CacheConfig(cacheNames = "operationLog")
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    @Cacheable
    public List<OperationLog> listNewest() {
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.select(OperationLog.Table.ID, OperationLog.Table.REQUEST_IP, OperationLog.Table.ADDRESS, OperationLog.Table.USERNAME, OperationLog.Table.CREATE_TIME, OperationLog.Table.DESCRIPTION, OperationLog.Table.STATUS)
                .orderByDesc(OperationLog.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);

        return operationLogMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public List<ViewDateVO> countByLast7Days() {
        return operationLogMapper.countByLast7Days();
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void save(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }

    @Override
    @Cacheable
    public Page<OperationLog> listTableByPage(Integer current, Integer size, LogQuery logQuery) {
        Page<OperationLog> page = new Page<>(current, size);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc(OperationLog.Table.CREATE_TIME);
        if (!StringUtils.isEmpty(logQuery.getAddress())) {
            wrapper.like(OperationLog.Table.ADDRESS,logQuery.getAddress());
        }
        if (!StringUtils.isEmpty(logQuery.getRequestIp())) {
            wrapper.like( OperationLog.Table.REQUEST_IP,logQuery.getRequestIp());
        }
        if (!StringUtils.isEmpty(logQuery.getDescription())) {
            wrapper.like(OperationLog.Table.DESCRIPTION,logQuery.getDescription());
        }
        if (!StringUtils.isEmpty(logQuery.getUsername())) {
            wrapper.like(OperationLog.Table.USERNAME,logQuery.getUsername());
        }
        if (logQuery.getStartDate() != null && logQuery.getEndDate() != null) {
            wrapper.between(OperationLog.Table.CREATE_TIME, logQuery.getStartDate(),logQuery.getEndDate());
        }
        if (logQuery.getTimeRank() != null){
            if(Objects.equals(logQuery.getTimeRank(),Constant.LOW_REQUEST_TIME_RANK)){
                wrapper.lt(OperationLog.Table.TIME,Constant.LOW_REQUEST_TIME);
            }
            if(Objects.equals(logQuery.getTimeRank(),Constant.MID_REQUEST_TIME_RANK)){
                wrapper.between(OperationLog.Table.TIME,Constant.LOW_REQUEST_TIME,Constant.HIGH_REQUEST_TIME);
            }
            if(Objects.equals(logQuery.getTimeRank(),Constant.HIGH_REQUEST_TIME_RANK)){
                wrapper.gt(OperationLog.Table.TIME,Constant.HIGH_REQUEST_TIME);
            }
        }
            return operationLogMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        operationLogMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        operationLogMapper.deleteBatchIds(idList);
    }

    @Override
    @Cacheable
    public Long countAll() {
        return Long.valueOf(operationLogMapper.selectCount(null));
    }

}
