package cn.fantasyblog.service.impl;

import cn.fantasyblog.dao.LocalStorageMapper;
import cn.fantasyblog.entity.LocalStorage;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.FileQuery;
import cn.fantasyblog.service.LocalStorageService;
import cn.fantasyblog.utils.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/12 20:26
 */
@Service
@CacheConfig(cacheNames = "localStorage")
public class LocalStorageServiceImpl implements LocalStorageService {

    // 从配置文件中读取
    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    @Autowired
    private LocalStorageMapper localStorageMapper;

    @Override
    @Cacheable
    public Page<LocalStorage> listTableByPage(Integer current, Integer size, FileQuery fileQuery) {
        Page<LocalStorage> page = new Page<>(current, size);
        QueryWrapper<LocalStorage> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(fileQuery.getName())) {
            wrapper.eq(LocalStorage.Table.NAME, fileQuery.getName());
        }
        if (fileQuery.getStartDate() != null && fileQuery.getEndDate() != null) {
            wrapper.between(LocalStorage.Table.CREATE_TIME, fileQuery.getStartDate(), fileQuery.getEndDate());
        }
        return localStorageMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeById(Long id) {
        LocalStorage localStorage = localStorageMapper.selectById(id);
        FileUtil.del(localStorage.getPath());
        localStorageMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        List<LocalStorage> localStorages = localStorageMapper.selectBatchIds(idList);
        for (LocalStorage file : localStorages) {
            FileUtil.del(file.getPath());
        }
        localStorageMapper.deleteBatchIds(idList);
    }

    // 检查大小
    // 获取文件格式、类型、原始名、命名、大小、时间
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void save(MultipartFile[] files) {
        for (MultipartFile file : files) {
            FileUtil.checkSize(maxSize,file.getSize());
            // 文件格式(上传时的文件名)
            String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
            String type = FileUtil.getFileType(suffix);
            // File.separator作用相当于\,系统默认的分割符号
            File upload = FileUtil.upload(file, path + type + File.separator);
            if(ObjectUtil.isNull(upload)) throw new BadRequestException("上传失败");
            try {
                String fileNameNoEx = FileUtil.getFileNameNoEx(file.getOriginalFilename());
                LocalStorage localStorage = new LocalStorage();
                localStorage.setName(fileNameNoEx)
                        .setSize(FileUtil.getSize(file.getSize()))
                        .setPath(upload.getPath())
                        .setType(type)
                        .setSuffix(suffix)
                        .setRealName(upload.getName())
                        .setCreateTime(new Date())
                        .setUpdateTime(localStorage.getCreateTime());
                localStorageMapper.insert(localStorage);
            } catch (Exception e){
                FileUtil.del(upload);
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(LocalStorage localStorage) {
        localStorageMapper.updateById(localStorage);
    }

    @Override
    public LocalStorage getById(Long id) {
        QueryWrapper<LocalStorage> wrapper = new QueryWrapper<>();
        wrapper.select(LocalStorage.Table.ID,LocalStorage.Table.NAME).eq(LocalStorage.Table.ID,id);
        return localStorageMapper.selectById(id);
    }
}
