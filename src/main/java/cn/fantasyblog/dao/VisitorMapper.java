package cn.myBlog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.myBlog.entity.Visitor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public interface VisitorMapper extends BaseMapper<Visitor> {

    /**
     * 根据ID查询访客
     * @return
     */
    Visitor selectById(Set<Visitor> map);

    /**
     * 查询
     * @param map
     * @return
     */
    Visitor selectByVisitorId(Map<String, Object> map);
}
