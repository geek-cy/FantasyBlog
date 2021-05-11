package cn.myBlog.service.impl;

import cn.myBlog.common.Constant;
import cn.myBlog.common.TableConstant;
import cn.myBlog.entity.Message;
import cn.myBlog.service.MessageService;
import cn.myBlog.vo.AuditVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.dao.MessageMapper;
import cn.myBlog.query.MessageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-18 22:21
 */
@Service
@CacheConfig(cacheNames = "message")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public void save(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public Page<Message> listPreviewByPage(Integer current, Integer size) {
        Page<Message> page = new Page<>(current, size);
        return messageMapper.listPreviewByPage(page);
    }

    @Override
    @Cacheable
    public Long countAll() {
        return Long.valueOf(messageMapper.selectCount(null));
    }

    @Override
    @Cacheable
    public List<Message> listNewest() {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.select(Message.Table.ID, Message.Table.NICKNAME, Message.Table.CREATE_TIME, Message.Table.CONTENT, Message.Table.STATUS)
                .orderByDesc(Message.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return messageMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public Page<Message> listTableByPage(Integer current, Integer size, MessageQuery messageQuery) {
        Page<Message> page = new Page<>(current, size);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(messageQuery.getNickname())) {
            wrapper.like(Message.Table.NICKNAME, messageQuery.getNickname());
        }
        if (messageQuery.getStartDate() != null && messageQuery.getEndDate() != null) {
            wrapper.between(Message.Table.CREATE_TIME, messageQuery.getStartDate(), messageQuery.getEndDate());
        }
        if (messageQuery.getStatus() != null) {
            wrapper.eq(Message.Table.STATUS, messageQuery.getStatus());
        }
        return messageMapper.listTableByPage(page, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        messageMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        messageMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void audit(AuditVO auditVO) {
        Message message = new Message();
        message.setId(auditVO.getId());
        message.setStatus(auditVO.getStatus());
        messageMapper.updateById(message);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void reply(Message message) {
        messageMapper.insert(message);
    }
}
