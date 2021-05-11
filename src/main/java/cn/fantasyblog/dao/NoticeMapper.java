package cn.fantasyblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fantasyblog.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
}
