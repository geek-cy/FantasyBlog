package cn.fantasyblog.service.impl;

import cn.fantasyblog.dao.LinkMapper;
import cn.fantasyblog.entity.Link;
import cn.fantasyblog.service.LinkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.LinkQuery;
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
 * @Date 2021-04-11 19:58
 */
@Service
@CacheConfig(cacheNames = "link")
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkMapper linkMapper;

    @Override
    @Cacheable
    public Page<Link> listTableByPage(Integer current, Integer size, LinkQuery linkQuery) {
        Page<Link> page = new Page<>(current, size);
        QueryWrapper<Link> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(linkQuery.getNickname())) {
            wrapper.like(Link.Table.NICKNAME, linkQuery.getNickname());
        }
        if (linkQuery.getStartDate() != null && linkQuery.getEndDate() != null) {
            wrapper.between(Link.Table.CREATE_TIME, linkQuery.getStartDate(), linkQuery.getEndDate());
        }
        if (linkQuery.getStatus() != null) {
            wrapper.eq(Link.Table.STATUS, linkQuery.getStatus());
        }
        return linkMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Cacheable
    public Page<Link> listPreviewByPage(Integer current, Integer size) {
        Page<Link> page = new Page<>(current,size);
        return linkMapper.selectPage(page,null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        linkMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        linkMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOrUpdate(Link link) {
        if (link.getId() == null) {
            QueryWrapper<Link> wrapper = new QueryWrapper<>();
            wrapper.eq(Link.Table.NICKNAME, link.getNickname());
            if (linkMapper.selectOne(wrapper) != null) {
                throw new BadRequestException("昵称相同");
            }
            wrapper.clear();
            wrapper.eq(Link.Table.LINK, link.getLink());
            if (linkMapper.selectOne(wrapper) != null) {
                throw new BadRequestException("网址相同");
            }
            linkMapper.insert(link);
        } else {
            QueryWrapper<Link> wrapper = new QueryWrapper<>();
            wrapper.eq(Link.Table.NICKNAME, link.getNickname());
            List<Link> linkNames = linkMapper.selectList(wrapper);
            linkNames = linkNames.stream().filter(l -> !l.getId().equals(link.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(linkNames)) {
                throw new BadRequestException("昵称相同");
            }
            wrapper.clear();
            wrapper.eq(Link.Table.LINK, link.getLink());
            List<Link> linkLinks = linkMapper.selectList(wrapper);
            linkLinks = linkNames.stream().filter(l -> !l.getId().equals(link.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(linkLinks)) {
                throw new BadRequestException("网址相同");
            }
            linkMapper.updateById(link);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void audit(Link link) {
        linkMapper.updateById(link);
    }

    @Override
    @Cacheable
    public Link getId(Long id) {
        return linkMapper.selectById(id);
    }
}
