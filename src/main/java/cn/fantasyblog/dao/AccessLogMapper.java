package cn.fantasyblog.dao;

import cn.fantasyblog.entity.Article;
import cn.fantasyblog.vo.ViewDateVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fantasyblog.entity.AccessLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface AccessLogMapper extends BaseMapper<AccessLog> {
    /**
     * 统计最近7天的前台流量并按天分组
     */
    List<ViewDateVO> countByLast7Days();

    /**
     * 延迟关联分页查询
     */
    List<AccessLog> listTableByPage(@Param("current") Integer current,@Param("pageSize") Integer pageSize,@Param("ew") QueryWrapper<AccessLog> queryWrapper);


}
