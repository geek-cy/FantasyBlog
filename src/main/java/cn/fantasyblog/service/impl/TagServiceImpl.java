package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.TableConstant;
import cn.fantasyblog.dao.TagMapper;
import cn.fantasyblog.entity.Tag;
import cn.fantasyblog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.TagQuery;
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
 * @Date 2021-03-21 19:32
 */
@Service
@CacheConfig(cacheNames = "tag")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    @Cacheable
    public List<Tag> listByArticleId(Long id) {
        return tagMapper.selectByArticleId(id);
    }

    @Override
    @Cacheable
    public List<Tag> listAll() {
        return tagMapper.selectList(null);
    }

    @Override
    @Cacheable
    public Page<Tag> listTableByTag(Integer current, Integer size, TagQuery tagQuery) {
        Page<Tag> page = new Page<>(current, size);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(tagQuery.getName())) {
            wrapper.like(Tag.Table.NAME, tagQuery.getName());
        }
        if (tagQuery.getStartDate() != null && tagQuery.getEndDate() != null) {
            wrapper.between(TableConstant.TAG_ALIAS + Tag.Table.CREATE_TIME, tagQuery.getStartDate(), tagQuery.getEndDate());
        }
        return tagMapper.listTableByTag(page,wrapper);
    }

    @Override
    @Cacheable
    public Long countAll() {
        return Long.valueOf(tagMapper.selectCount(null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        tagMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        tagMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOrUpdate(Tag tag) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq(Tag.Table.NAME, tag.getName());
        if (tag.getId() == null) {
            if (tagMapper.selectOne(wrapper) != null) {
                throw new BadRequestException("标签名称已存在");
            }
            tagMapper.insert(tag);
        } else {
            List<Tag> tags = tagMapper.selectList(wrapper);
            tags = tags.stream().filter(t -> !t.getName().equals(tag.getName())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tags)) {
                throw new BadRequestException("标签名称已存在");
            }
            tagMapper.updateById(tag);
        }
    }

    @Override
    @Cacheable
    public Tag getById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    @Cacheable
    public List<String> color() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select(Tag.Table.COLOR).groupBy(Tag.Table.COLOR);
        List<Tag> tags = tagMapper.selectList(wrapper);
        return tags.stream().map(Tag::getColor).collect(Collectors.toList());
    }

    @Override
    @Cacheable
    public List<Tag> listArticleCountByTag() {
        return tagMapper.listArtcileCountByTag();
    }
}
