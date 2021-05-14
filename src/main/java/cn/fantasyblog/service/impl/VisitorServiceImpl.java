package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dao.VisitorMapper;
import cn.fantasyblog.entity.Visitor;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.VisitorService;
import cn.fantasyblog.vo.VisitorVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.VisitorQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;


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
        visitorMapper.insert(visitor);
    }

    @Override
    public Visitor login(VisitorVO visitorVO) {
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.select(Visitor.Table.ID,Visitor.Table.USERNAME,Visitor.Table.PASSWORD,Visitor.Table.NICKNAME,
                Visitor.Table.AVATAR,Visitor.Table.LINK,Visitor.Table.EMAIL,Visitor.Table.STATUS)
                .eq(Visitor.Table.USERNAME,visitorVO.getCertificate())
                .or()
                .eq(Visitor.Table.EMAIL,visitorVO.getCertificate());
        Visitor visitor = visitorMapper.selectOne(wrapper);
        try {
            if (visitor.getStatus().equals(Constant.VISITOR_DISABLE)) {
                throw new BadRequestException("用户已被停用");
            }
            if(!visitor.getPassword().equals(visitorVO.getPassword())){
                throw new BadRequestException("用户名或密码错误");
            }
        } catch (NullPointerException e){
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

}
