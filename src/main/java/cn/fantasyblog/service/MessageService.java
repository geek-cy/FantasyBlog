package cn.myBlog.service;

import cn.myBlog.entity.Message;
import cn.myBlog.vo.AuditVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.myBlog.query.MessageQuery;

import java.util.List;

public interface MessageService {

    /**
     * 添加留言
     */
    void save(Message message);

    /**
     * 前台查询留言
     */
    Page<Message> listPreviewByPage(Integer current, Integer size);

    /**
     * 留言数量
     */
    Long countAll();

    /**
     * 最近留言
     */
    List<Message> listNewest();

    /**
     * 后台分页查询留言
     */
    Page<Message> listTableByPage(Integer current, Integer size, MessageQuery messageQuery);

    /**
     * 删除留言
     */
    void remove(Long id);

    /**
     * 批量删除留言
     */
    void removeList(List<Long> idList);

    /**
     * 审核留言
     */
    void audit(AuditVO auditVO);

    /**
     * 回复留言
     */
    void reply(Message message);

}
