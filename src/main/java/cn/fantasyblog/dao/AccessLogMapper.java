package cn.myBlog.dao;

import cn.myBlog.vo.ViewDateVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.myBlog.entity.AccessLog;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface AccessLogMapper extends BaseMapper<AccessLog> {
    /**
     * 统计最近7天的前台流量
     */
    List<ViewDateVO> countByLast7Days();
}