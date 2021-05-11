package cn.myBlog.service.impl;

import cn.myBlog.common.TableConstant;
import cn.myBlog.dao.CategoryMapper;
import cn.myBlog.entity.Category;
import cn.myBlog.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.exception.BadRequestException;
import cn.myBlog.query.CategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-22 21:03
 */
@Service
@CacheConfig(cacheNames = "category")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Page<Category> listTableByCategory(int current, int size, CategoryQuery categoryQuery) {
//        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select(Category.Table.ID,Category.Table.NAME,Category.Table.COLOR,Category.Table.DISPLAY);
//        return categoryMapper.selectList(queryWrapper);
        Page<Category> page = new Page<>(current, size);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(categoryQuery.getName())) {
            wrapper.like(Category.Table.NAME, categoryQuery.getName());
        }
        if (categoryQuery.getDisplay() != null) {
            wrapper.eq(Category.Table.DISPLAY, categoryQuery.getDisplay());
        }
        if (categoryQuery.getStartDate() != null && categoryQuery.getEndDate() != null) {
            // 此处createTime若无前缀则摸棱两可
            wrapper.between(TableConstant.CATE_ALIAS + Category.Table.CREATE_TIME, categoryQuery.getStartDate(), categoryQuery.getEndDate());
        }
        return categoryMapper.listTableByCategory(page, wrapper);
    }

    @Override
    public Long countAll() {
        return Long.valueOf(categoryMapper.selectCount(null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeList(List<Long> idList) {
        categoryMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Category category) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq(Category.Table.NAME, category.getName());
        if (category.getId() == null) {
            // 检查名字是否存在
            if (categoryMapper.selectOne(wrapper) != null) {
                throw new BadRequestException("分类名称已存在");
            }
            categoryMapper.insert(category);
        } else {
            List<Category> categories = categoryMapper.selectList(wrapper);
            // 更新自己时候把自己信息过滤掉即留下不是自己的id
            categories = categories.stream().filter(c -> !c.getId().equals(category.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(categories)) {
                throw new BadRequestException("分类名称已存在");
            }
            categoryMapper.updateById(category);
        }
    }

    @Override
    public Category getById(Long id) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq(Category.Table.ID, id);
        return categoryMapper.selectOne(wrapper);
    }

    @Override
    public List<Category> listAll() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.select(Category.Table.ID, Category.Table.NAME);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public List<String> listColor() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.select(Category.Table.COLOR).groupBy(Category.Table.COLOR);
        List<Category> categories = categoryMapper.selectList(wrapper);
        return categories.stream().map(Category::getColor).collect(Collectors.toList());
    }

    @Override
    public List<Category> listArticleCountByCategory() {
        return categoryMapper.listPreviewByCategory();
    }
}
