package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.TableConstant;
import cn.fantasyblog.dao.RoleUserMapper;
import cn.fantasyblog.dao.UserMapper;
import cn.fantasyblog.entity.RoleUser;
import cn.fantasyblog.entity.User;
import cn.fantasyblog.service.UserService;
import cn.fantasyblog.utils.StringUtils;
import cn.fantasyblog.vo.UserInfoVO;
import cn.fantasyblog.vo.UserLoginVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.VisitorQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-04 20:45
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Override
    @Cacheable
    public Page<User> listTableByPage(int current, int size, VisitorQuery userQuery) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userQuery.getUsername())) {
            wrapper.like(User.Table.USERNAME, userQuery.getUsername());
        }
        if (!StringUtils.isEmpty(userQuery.getEmail())) {
            wrapper.like(User.Table.EMAIL, userQuery.getEmail());
        }
        if (userQuery.getStartDate() != null && userQuery.getEndDate() != null) {
            wrapper.between(TableConstant.USER_ALIAS + User.Table.CREATE_TIME, userQuery.getStartDate(), userQuery.getEndDate());
        }
        return userMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        userMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOfUpdate(User user) {
        if (user.getId() == null) {
            //保存
            //验证用户名是否唯一
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq(User.Table.USERNAME, user.getUsername());
            if (null != userMapper.selectOne(wrapper)) {
                throw new BadRequestException("用户名已存在");
            }
            //验证手机号码是否唯一
            wrapper.clear();
            wrapper.eq(User.Table.PHONE, user.getPhone());
            if (null != userMapper.selectOne(wrapper)) {
                throw new BadRequestException("手机号已存在");
            }
            //验证邮箱是否唯一
            wrapper.clear();
            wrapper.eq(User.Table.EMAIL, user.getEmail());
            if (null != userMapper.selectOne(wrapper)) {
                throw new BadRequestException("邮箱已存在");
            }
            userMapper.insert(user);
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(user.getId());
            roleUser.setRoleId(user.getRoleId());
            roleUserMapper.insert(roleUser);
        }
        else{
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq(User.Table.PHONE,user.getPhone());
            List<User> phones = userMapper.selectList(wrapper);
            phones = phones.stream().filter(u -> !u.getId().equals(user.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(phones)){
                throw new BadRequestException("手机号已存在");
            }
            wrapper.clear();
            wrapper.eq(User.Table.EMAIL,user.getEmail());
            List<User> emails = userMapper.selectList(wrapper);
            emails = emails.stream().filter(u -> !u.getId().equals(user.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(emails)){
                throw new BadRequestException("邮箱已存在");
            }
            // 首先更新用户
            userMapper.updateById(user);
            Long uid = user.getId();
            Long rid = user.getRoleId();
            roleUserMapper.updateRoleId(rid,uid);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    public void changeStatus(User user) {
        userMapper.updateById(user);
    }

    @Override
    @Cacheable
    public User getById(Long id) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.select(User.Table.ID,User.Table.USERNAME,User.Table.NICKNAME,User.Table.SEX, User.Table.PHONE, User.Table.EMAIL, User.Table.STATUS).eq(User.Table.ID,id);
        User user = userMapper.selectOne(userWrapper);
        QueryWrapper<RoleUser> roleUserWrapper = new QueryWrapper<>();
        roleUserWrapper.select(RoleUser.Table.ROLE_ID).eq(RoleUser.Table.USER_ID,id);
        List<RoleUser> roleUsers = roleUserMapper.selectList(roleUserWrapper);
        if(!CollectionUtils.isEmpty(roleUsers)){
            user.setRoleId(roleUsers.get(0).getRoleId());
        }
        return user;
    }

    @Override
    @Cacheable
    public Long countAll() {
        return Long.valueOf(userMapper.selectCount(null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(UserLoginVO userLoginVO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select(User.Table.ID,User.Table.PASSWORD).eq(User.Table.ID,userLoginVO.getUserId());
        User user = userMapper.selectOne(wrapper);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 明文与密文对比是否一致
        if(!encoder.matches(userLoginVO.getOldPassword(),user.getPassword())){
            throw new BadRequestException("旧密码输入不正确");
        }
        user.setPassword(encoder.encode(userLoginVO.getNewPassword()));
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void updateInfo(UserInfoVO userInfoVO) {
        //验证手机号码是否唯一
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.Table.PHONE,userInfoVO.getPhone());
        List<User> users = userMapper.selectList(wrapper);
        users = users.stream().filter(u->!u.getId().equals(userInfoVO.getId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(users)){
            throw new BadRequestException("用户手机号已存在");
        }
        //验证邮箱是否唯一
        wrapper.clear();
        wrapper.eq(User.Table.EMAIL,userInfoVO.getEmail());
        users = userMapper.selectList(wrapper);
        users = users.stream().filter(u->!u.getId().equals(userInfoVO.getId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(users)){
            throw new BadRequestException("用户邮箱已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userInfoVO,user);
        userMapper.updateById(user);
    }
}
