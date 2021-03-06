package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Photo;
import cn.fantasyblog.service.PhotoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.dao.PhotoMapper;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.PhotoQuery;
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
 * @Date 2021-04-14 15:47
 */
@Service
@CacheConfig(cacheNames = "photo")
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoMapper photoMapper;

    @Override
    @Cacheable
    public Page<Photo> listTableByPage(Integer current, Integer size, PhotoQuery photoQuery) {
        Page<Photo> page = new Page<>(current, size);
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(photoQuery.getDescription())) {
            wrapper.like(Photo.Table.DESCRIPTION, photoQuery.getDescription());
        }
        if (photoQuery.getStartDate() != null && photoQuery.getEndDate() != null) {
            wrapper.between(Photo.Table.CREATE_TIME, photoQuery.getStartDate(),photoQuery.getEndDate());
        }
        if (photoQuery.getDisplay() != null) {
            wrapper.eq(Photo.Table.DISPLAY, photoQuery.getDisplay());
        }
        return photoMapper.listTableBYPage(page, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        photoMapper.deleteById(id);
        photoMapper.updateID();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeList(List<Long> idList) {
        photoMapper.deleteBatchIds(idList);
        photoMapper.updateID();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Photo photo) {
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        wrapper.eq(Photo.Table.URL,photo.getUrl());
        if(photo.getId() == null){
            if(photoMapper.selectOne(wrapper)!=null){
                throw new BadRequestException("??????????????????");
            }
            photoMapper.insert(photo);
        }else {
            List<Photo> photos = photoMapper.selectList(wrapper);
            photos = photos.stream().filter(p->!p.getId().equals(photo.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(photos)){
                throw new BadRequestException("??????????????????");
            }
            photoMapper.updateById(photo);
        }
    }

    @Override
    @Cacheable
    public Photo getId(Long id) {
        return photoMapper.selectById(id);
    }

    @Override
    @Cacheable
    public List<Photo> listAll() {
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        wrapper.select(Photo.Table.URL).orderByAsc(Photo.Table.SORT).eq(Photo.Table.DISPLAY, Constant.DISPLAY);
        return photoMapper.selectList(wrapper);
    }
}
