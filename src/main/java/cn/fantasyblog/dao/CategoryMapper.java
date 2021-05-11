package cn.fantasyblog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-22 21:04
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 前台查询所有分类（统计文章数目）
     * @return
     */
    List<Category> listPreviewByCategory();

    /**
     * 后台分页查询所有分类
     * @return
     */
    Page<Category> listTableByCategory(IPage<Category> page, @Param("ew")QueryWrapper<Category> ew);
}
