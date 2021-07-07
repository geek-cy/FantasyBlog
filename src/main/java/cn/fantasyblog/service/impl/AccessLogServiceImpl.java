package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.TableConstant;
import cn.fantasyblog.dao.AccessLogMapper;
import cn.fantasyblog.entity.AccessLog;
import cn.fantasyblog.service.AccessLogService;
import cn.fantasyblog.utils.StringUtils;
import cn.fantasyblog.vo.ViewDateVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.LogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-13 23:18
 */
@Service
@CacheConfig(cacheNames = "accessLog")
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(AccessLog log) {
        accessLogMapper.insert(log);
    }

    @Override
    @Cacheable
    public List<AccessLog> listByPage(Integer current, Integer size, LogQuery logQuery) {
        // 实体对象封装操作类
        QueryWrapper<AccessLog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(logQuery.getRequestIp())) {
            wrapper.like(AccessLog.Table.REQUEST_IP, logQuery.getRequestIp());
        }
        if (!StringUtils.isEmpty(logQuery.getAddress())) {
            wrapper.like(AccessLog.Table.ADDRESS, logQuery.getAddress());
        }
        if (!StringUtils.isEmpty(logQuery.getDescription())) {
            wrapper.like(AccessLog.Table.DESCRIPTION, logQuery.getDescription());
        }
        if (!StringUtils.isEmpty(logQuery.getBrowser())) {
            wrapper.like(AccessLog.Table.BROWSER, logQuery.getBrowser());
        }
        if (logQuery.getStartDate() != null && logQuery.getEndDate() != null) {
            wrapper.between(AccessLog.Table.CREATE_TIME, logQuery.getStartDate(), logQuery.getEndDate());
        }
        if (logQuery.getTimeRank() != null) {
            if (Objects.equals(logQuery.getTimeRank(), Constant.LOW_REQUEST_TIME_RANK)) {
                wrapper.lt(AccessLog.Table.TIME, Constant.LOW_REQUEST_TIME);
            }
            if (Objects.equals(logQuery.getTimeRank(), Constant.MID_REQUEST_TIME_RANK)) {
                wrapper.between(AccessLog.Table.TIME, Constant.LOW_REQUEST_TIME, Constant.HIGH_REQUEST_TIME);
            }
            if (Objects.equals(logQuery.getTimeRank(), Constant.HIGH_REQUEST_TIME_RANK)) {
                wrapper.gt(AccessLog.Table.TIME, Constant.HIGH_REQUEST_TIME);
            }
        }
        return accessLogMapper.listTableByPage(current,size,wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeById(Long id) {
        accessLogMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeByIdList(List<Long> idList) {
        accessLogMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Long countAll() {
        return Long.valueOf(accessLogMapper.selectCount(null));
    }

    @Override
    @Cacheable
    public List<AccessLog> listNewest() {
        QueryWrapper<AccessLog> wrapper = new QueryWrapper<>();
        wrapper.select(AccessLog.Table.ID, AccessLog.Table.REQUEST_IP, AccessLog.Table.ADDRESS, AccessLog.Table.CREATE_TIME, AccessLog.Table.DESCRIPTION, AccessLog.Table.STATUS)
                .orderByDesc(AccessLog.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return accessLogMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public List<ViewDateVO> countByLast7Days() {
        return accessLogMapper.countByLast7Days();
    }

}
