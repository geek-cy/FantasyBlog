package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dao.VisitorMapper;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.service.MailService;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.VisitorService;
import cn.fantasyblog.utils.MD5Util;
import cn.fantasyblog.vo.VisitorVO;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.VisitorQuery;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Objects;


/**
 * @Description
 * @Author Cy
 * @Date 2021-04-02 19:38
 */
@Service
@CacheConfig(cacheNames = "visitor")
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private ITemplateEngine templateEngine;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void save(Visitor visitor) {
        //保存
        //验证用户名是否唯一
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.eq(Visitor.Table.USERNAME, visitor.getUsername());
        if (null != visitorMapper.selectOne(wrapper)) {
            throw new BadRequestException("用户名已存在");
        }

        //验证邮箱是否唯一
        wrapper.clear();
        wrapper.eq(Visitor.Table.EMAIL, visitor.getEmail());
        if (null != visitorMapper.selectOne(wrapper)) {
            throw new BadRequestException("邮箱已存在");
        }

        //验证昵称是否唯一
        wrapper.clear();
        wrapper.eq(Visitor.Table.NICKNAME, visitor.getNickname());
        if (null != visitorMapper.selectOne(wrapper)) {
            throw new BadRequestException("昵称已存在");
        }

        // 先添加才有id,自带keyProperty="id",返回得到主键
        visitorMapper.insert(visitor);

        String code = MD5Util.code(visitor.getPassword());
        if (code == null) {
            throw new BadRequestException("注册出现异常，请联系管理员");
        }

        // 邮箱验证访客
        // 访问模板，给模板传参，利用context构造参数
        try {
            Context context = new Context();
            context.setVariable("email", visitor.getEmail());
            String url = Constant.DOMAIN + "/visitor/activation/" + visitor.getId() + "/" + code;
            context.setVariable("url", url);
            String content = templateEngine.process("/front/activation", context);
            mailService.sendHtmlMail(visitor.getEmail(), Constant.ACTIVATION_EMAIL, content);
            redisTemplate.opsForValue().set(visitor.getUsername(), code);
        } catch (MessagingException e) {
            throw new BadRequestException("注册出现异常，请联系管理员");
        }
    }

    @Override
    public String activation(Long id, String code) {
//        Visitor visitor = visitorMapper.selectById(id); 这里失效只能查部分属性，原因暂未知
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.select(Visitor.Table.ID, Visitor.Table.STATUS, Visitor.Table.USERNAME).eq(Visitor.Table.ID, id);
        Visitor visitor = visitorMapper.selectOne(wrapper);
        if (visitor.getStatus().equals(Constant.VISITOR_ENABLE)) {
            return "该账号已激活过";
        } else if (Objects.equals(redisTemplate.opsForValue().get(visitor.getUsername()), code)) {
            visitor.setStatus(Constant.VISITOR_ENABLE);
            visitorMapper.updateById(visitor);
            redisTemplate.delete(visitor.getUsername());
            return "激活成功";
        }
        return "激活失败，激活码不正确";
    }

    @Override
    public Visitor login(VisitorVO visitorVO) {
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.select(Visitor.Table.ID, Visitor.Table.USERNAME, Visitor.Table.PASSWORD, Visitor.Table.NICKNAME,
                Visitor.Table.AVATAR, Visitor.Table.LINK, Visitor.Table.EMAIL, Visitor.Table.STATUS)
                .eq(Visitor.Table.USERNAME, visitorVO.getCertificate())
                .or()
                .eq(Visitor.Table.EMAIL, visitorVO.getCertificate());
        Visitor visitor = visitorMapper.selectOne(wrapper);
        try {
            if (visitor.getStatus().equals(Constant.VISITOR_DISABLE)) {
                throw new BadRequestException("用户未激活或已被停用");
            }
            if (!visitor.getPassword().equals(visitorVO.getPassword())) {
                throw new BadRequestException("用户名或密码错误");
            }
        } catch (NullPointerException e) {
            throw new BadRequestException("用户名/邮箱不存在");
        }
        visitor.setPassword(null);
        visitor.setStatus(null);
        return visitor;
    }

    @Override
    @Cacheable
    public Page<Visitor> listTableByPage(Integer current, Integer size, VisitorQuery visitorQuery) {
        Page<Visitor> page = new Page<>(current, size);
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.select(Visitor.Table.ID, Visitor.Table.USERNAME, Visitor.Table.NICKNAME, Visitor.Table.STATUS, Visitor.Table.EMAIL, Visitor.Table.LINK, Visitor.Table.CREATE_TIME, Visitor.Table.UPDATE_TIME);
        if (!StringUtils.isEmpty(visitorQuery.getUsername())) {
            wrapper.like(Visitor.Table.USERNAME, visitorQuery.getUsername());
        }
        if (!StringUtils.isEmpty(visitorQuery.getEmail())) {
            wrapper.like(Visitor.Table.EMAIL, visitorQuery.getEmail());
        }
        if (visitorQuery.getStartDate() != null && visitorQuery.getEndDate() != null) {
            wrapper.between(Visitor.Table.CREATE_TIME, visitorQuery.getStartDate(), visitorQuery.getEndDate());
        }
        return visitorMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        visitorMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        visitorMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void changeStatus(Visitor visitor) {
        visitorMapper.updateById(visitor);
    }

    @Override
    @Cacheable
    public Long countAll() {
        return Long.valueOf(visitorMapper.selectCount(null));
    }

    @Override
    @CacheEvict(allEntries = true)
    public void removeVisitors() {
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.select(Visitor.Table.ID).eq(Visitor.Table.STATUS, Constant.VISITOR_DISABLE);
        visitorMapper.delete(wrapper);
    }
}
