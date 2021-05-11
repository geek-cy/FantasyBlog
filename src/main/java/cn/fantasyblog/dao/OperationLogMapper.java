package cn.myBlog.dao;

import cn.myBlog.entity.OperationLog;
import cn.myBlog.vo.ViewDateVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    /**
     * 统计最近7天的操作次数
     */
    List<ViewDateVO> countByLast7Days();



}
