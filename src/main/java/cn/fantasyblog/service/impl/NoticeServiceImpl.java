package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.TableConstant;
import cn.fantasyblog.dao.NoticeMapper;
import cn.fantasyblog.entity.Notice;
import cn.fantasyblog.query.NoticeQuery;
import cn.fantasyblog.service.NoticeService;
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
@CacheConfig(cacheNames = "Notice")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper NoticeMapper;

    @Override
    @Cacheable
    public Page<Notice> listTableByPage(Integer current, Integer size, NoticeQuery NoticeQuery) {
        Page<Notice> page = new Page<>(current, size);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(NoticeQuery.getTitle())) {
            wrapper.like(Notice.Table.TITLE, NoticeQuery.getTitle());
        }
        if (!StringUtils.isEmpty(NoticeQuery.getContent())) {
            wrapper.like(Notice.Table.CONTENT, NoticeQuery.getContent());
        }
        if (NoticeQuery.getStartDate() != null && NoticeQuery.getEndDate() != null) {
            wrapper.between(Notice.Table.CREATE_TIME, NoticeQuery.getStartDate(), NoticeQuery.getEndDate());
        }
        if(NoticeQuery.getDisplay() != null){
            wrapper.eq(Notice.Table.DISPLAY,NoticeQuery.getDisplay());
        }
        return NoticeMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOrUpdate(Notice notice) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.select(Notice.Table.ID,Notice.Table.TITLE).eq(Notice.Table.TITLE, notice.getTitle());
        if (notice.getId() == null) {
            if(!StringUtils.isEmpty(NoticeMapper.selectOne(wrapper))){
                throw new RuntimeException("标题已经存在");
            }
            NoticeMapper.insert(notice);
        } else {
            List<Notice> notices = NoticeMapper.selectList(wrapper);
            notices = notices.stream().filter(t -> !t.getId().equals(notice.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(notices)) {
                throw new RuntimeException("标题已经存在");
            }
            NoticeMapper.updateById(notice);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        NoticeMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        NoticeMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void audit(Notice Notice) {
        NoticeMapper.updateById(Notice);
    }

    @Override
    @Cacheable
    public List<Notice> listNewest() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.select(Notice.Table.ID, Notice.Table.TITLE, Notice.Table.SORT, Notice.Table.DISPLAY, Notice.Table.CREATE_TIME, Notice.Table.UPDATE_TIME)
                .orderByDesc(Notice.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return NoticeMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public Notice getById(Long id) {
        return NoticeMapper.selectById(id);
    }
}
