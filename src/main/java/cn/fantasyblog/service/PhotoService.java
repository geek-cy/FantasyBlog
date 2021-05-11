package cn.fantasyblog.service;

import cn.fantasyblog.entity.Photo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.PhotoQuery;

import java.util.List;

public interface PhotoService {

    /**
     * 后台分页查询相册
     * @param current
     * @param size
     * @param photoQuery
     * @return
     */
    Page<Photo> listTableByPage(Integer current, Integer size, PhotoQuery photoQuery);

    /**
     * 删除相册
     * @param id
     */
    void remove(Long id);

    /**
     * 批量删除相册
     * @param idList
     */
    void removeList(List<Long> idList);

    /**
     * 添加或更新相册
     * @param photo
     */
    void saveOrUpdate(Photo photo);

    /**
     * 根据ID获取相册
     * @param id
     * @return
     */
    Photo getId(Long id);

    /**
     * 前台分页查询所有照片
     * @return
     */
    List<Photo> listAll();
}
