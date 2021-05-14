package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.TableConstant;
import cn.fantasyblog.dao.AnnounceMapper;
import cn.fantasyblog.entity.Announce;
import cn.fantasyblog.query.AnnounceQuery;
import cn.fantasyblog.service.AnnounceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-22 20:58
 */
@Service
@CacheConfig(cacheNames = "announce")
public class AnnounceServiceImpl implements AnnounceService {

    @Autowired
    private AnnounceMapper announceMapper;

    @Override
    @Cacheable
    public Page<Announce> listTableByPage(Integer current, Integer size, AnnounceQuery announceQuery) {
        Page<Announce> page = new Page<>(current, size);
        QueryWrapper<Announce> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(announceQuery.getTitle())) {
            wrapper.like(Announce.Table.TITLE, announceQuery.getTitle());
        }
        if (!StringUtils.isEmpty(announceQuery.getContent())) {
            wrapper.like(Announce.Table.CONTENT, announceQuery.getContent());
        }
        if (announceQuery.getStartDate() != null && announceQuery.getEndDate() != null) {
            wrapper.between(Announce.Table.CREATE_TIME, announceQuery.getStartDate(), announceQuery.getEndDate());
        }
        if(announceQuery.getDisplay() != null){
            wrapper.eq(Announce.Table.DISPLAY,announceQuery.getDisplay());
        }
        return announceMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOrUpdate(Announce announce) {
        QueryWrapper<Announce> wrapper = new QueryWrapper<>();
        wrapper.select(Announce.Table.TITLE).eq(Announce.Table.TITLE, announce.getTitle());
        if (announce.getId() == null) {
            if(!StringUtils.isEmpty(announceMapper.selectOne(wrapper))){
                throw new RuntimeException("标题已经存在");
            }
            announceMapper.insert(announce);
        } else {
            List<Announce> announces = announceMapper.selectList(wrapper);
            announces = announces.stream().filter(t -> !t.getId().equals(announce.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(announces)) {
                throw new RuntimeException("标题已经存在");
            }
            announceMapper.updateById(announce);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        announceMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        announceMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void audit(Announce announce) {
        announceMapper.updateById(announce);
    }

    @Override
    @Cacheable
    public List<Announce> listNewest() {
        QueryWrapper<Announce> wrapper = new QueryWrapper<>();
        wrapper.select(Announce.Table.ID, Announce.Table.TITLE, Announce.Table.SORT, Announce.Table.DISPLAY, Announce.Table.CREATE_TIME, Announce.Table.UPDATE_TIME)
                .orderByDesc(Announce.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return announceMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public Announce getById(Long id) {
        return announceMapper.selectById(id);
    }
}
