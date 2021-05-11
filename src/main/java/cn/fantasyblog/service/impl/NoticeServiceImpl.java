package cn.myBlog.service.impl;

import cn.myBlog.common.Constant;
import cn.myBlog.common.TableConstant;
import cn.myBlog.dao.NoticeMapper;
import cn.myBlog.entity.Notice;
import cn.myBlog.service.NoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.query.NoticeQuery;
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
@CacheConfig(cacheNames = "notice")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    @Cacheable
    public Page<Notice> listTableByPage(Integer current, Integer size, NoticeQuery noticeQuery) {
        Page<Notice> page = new Page<>(current, size);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(noticeQuery.getTitle())) {
            wrapper.like(Notice.Table.TITLE, noticeQuery.getTitle());
        }
        if (!StringUtils.isEmpty(noticeQuery.getContent())) {
            wrapper.like(Notice.Table.CONTENT, noticeQuery.getContent());
        }
        if (noticeQuery.getStartDate() != null && noticeQuery.getEndDate() != null) {
            wrapper.between(Notice.Table.CREATE_TIME, noticeQuery.getStartDate(), noticeQuery.getEndDate());
        }
        if(noticeQuery.getDisplay() != null){
            wrapper.eq(Notice.Table.DISPLAY,noticeQuery.getDisplay());
        }
        return noticeMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOrUpdate(Notice notice) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.select(Notice.Table.TITLE).eq(Notice.Table.TITLE, notice.getTitle());
        if (notice.getId() == null) {
            if(!StringUtils.isEmpty(noticeMapper.selectOne(wrapper))){
                throw new RuntimeException("标题已经存在");
            }
            noticeMapper.insert(notice);
        } else {
            List<Notice> notices = noticeMapper.selectList(wrapper);
            notices = notices.stream().filter(t -> !t.getId().equals(notice.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(notices)) {
                throw new RuntimeException("标题已经存在");
            }
            noticeMapper.updateById(notice);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        noticeMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        noticeMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void audit(Notice notice) {
        noticeMapper.updateById(notice);
    }

    @Override
    @Cacheable
    public List<Notice> listNewest() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.select(Notice.Table.ID,Notice.Table.TITLE,Notice.Table.SORT,Notice.Table.DISPLAY,Notice.Table.CREATE_TIME,Notice.Table.UPDATE_TIME)
                .orderByDesc(Notice.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return noticeMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public Notice getById(Long id) {
        return noticeMapper.selectById(id);
    }
}
