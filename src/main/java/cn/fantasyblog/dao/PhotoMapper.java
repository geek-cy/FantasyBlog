package cn.fantasyblog.dao;

import cn.fantasyblog.entity.Photo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoMapper extends BaseMapper<Photo> {

    Page<Photo> listTableBYPage(IPage<Photo> page, @Param("ew")QueryWrapper<Photo> queryWrapper);

    /**
     * 更新图片顺序
     */
    void updateID();
}
