package cn.fantasyblog.service.impl;

import cn.fantasyblog.dao.NoticeMapper;
import cn.fantasyblog.entity.Notice;
import cn.fantasyblog.query.NoticeQuery;
import cn.fantasyblog.service.NoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/14 17:02
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Page<Notice> listTableByPage(Integer current, Integer size, NoticeQuery noticeQuery) {
        Page<Notice> page = new Page<>(current, size);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (noticeQuery.getType() != null) {
            wrapper.eq(Notice.Table.TYPE, noticeQuery.getType());
        }
        if (noticeQuery.getStartDate() != null && noticeQuery.getEndDate() != null) {
            wrapper.between(Notice.Table.CREATE_TIME, noticeQuery.getStartDate(), noticeQuery.getEndDate());
        }
        return noticeMapper.selectPage(page,wrapper);
    }

    @Override
    public void remove(Long id) {
        noticeMapper.deleteById(id);
    }

    @Override
    public void removeList(List<Long> idList) {
        noticeMapper.deleteBatchIds(idList);
    }
}
