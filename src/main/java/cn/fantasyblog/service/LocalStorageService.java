package cn.fantasyblog.service;


import cn.fantasyblog.entity.LocalStorage;
import cn.fantasyblog.query.FileQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LocalStorageService {

    /**
     * 后台分页查询本地文件
     */
    Page<LocalStorage> listTableByPage(Integer current, Integer size, FileQuery fileQuery);

    /**
     * 删除文件
     */
    void removeById(Long id);

    /**
     * 批量删除文件
     */
    void removeList(List<Long> idList);

    /**
     * 上传文件
     */
    void save(MultipartFile[] files);

    /**
     * 更新文件
     */
    void update(LocalStorage localStorage);

    /**
     * 根据ID查询文件
     */
    LocalStorage getById(Long id);
}
