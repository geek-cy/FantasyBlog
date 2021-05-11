package cn.myBlog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.myBlog.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
}
