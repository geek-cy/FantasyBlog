package cn.fantasyblog.service.impl;

import cn.fantasyblog.dao.RoleMenuMapper;
import cn.fantasyblog.dao.UserMapper;
import cn.fantasyblog.entity.Role;
import cn.fantasyblog.entity.RoleMenu;
import cn.fantasyblog.entity.RoleUser;
import cn.fantasyblog.entity.User;
import cn.fantasyblog.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.dao.RoleMapper;
import cn.fantasyblog.dao.RoleUserMapper;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.query.RoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-07 21:06
 */
@Service
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    @Cacheable
    public Page<Role> listTableByPage(Integer current, Integer size, RoleQuery roleQuery) {
        Page<Role> page = new Page<>(current, size);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roleQuery.getRoleName())) {
            wrapper.eq(Role.Table.ROLE_NAME, roleQuery.getRoleName());
        }
        if (!StringUtils.isEmpty(roleQuery.getDescription())) {
            wrapper.eq(Role.Table.DESCRIPTION, roleQuery.getDescription());
        }
        if (roleQuery.getStartDate() != null && roleQuery.getEndDate() != null) {
            wrapper.between(Role.Table.CREATE_TIME, roleQuery.getStartDate(), roleQuery.getEndDate());
        }
        return roleMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Cacheable
    public List<Role> listAll() {
        return roleMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void remove(Long id) {
        QueryWrapper<RoleUser> wrapper = new QueryWrapper<>();
        wrapper.eq(RoleUser.Table.ROLE_ID,id);
        Integer count = roleUserMapper.selectCount(wrapper);
        if(count >= 1){
            throw new BadRequestException("??????????????????????????????????????????");
        }
        roleMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void removeList(List<Long> idList) {
        for(Long id:idList){
            QueryWrapper<RoleUser> wrapper = new QueryWrapper<>();
            wrapper.eq(RoleUser.Table.ROLE_ID,id);
            Integer count = roleUserMapper.selectCount(wrapper);
            if(count >= 1){
                QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
                roleWrapper.select(Role.Table.ROLE_NAME).eq(Role.Table.ID,id);
                Role role = roleMapper.selectOne(roleWrapper);
                throw new BadRequestException("??????"+role+"?????????????????????????????????");
            }
        }
        roleMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOrUpdate(Role role) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq(Role.Table.ROLE_NAME, role.getRoleName());
        if (role.getId() == null) {
            if (roleMapper.selectOne(wrapper) != null) {
                throw new BadRequestException("?????????????????????");
            }
            // ????????????
            roleMapper.insert(role);
            // ??????????????????
            roleMenuMapper.insertBatch(role.getId(),role.getMenuIdList());
        } else {
            List<Role> roles = roleMapper.selectList(wrapper);
            roles = roles.stream().filter(r->!r.getId().equals(role.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(roles)){
                throw new BadRequestException("?????????????????????");
            }
            // ????????????
            roleMapper.updateById(role);
            // ??????????????????
            QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
            roleMenuWrapper.eq(RoleMenu.Table.ROLE_ID,role.getId());
            // ????????????????????????
            roleMenuMapper.delete(roleMenuWrapper);
            //???????????????????????????
            roleMenuMapper.insertBatch(role.getId(), role.getMenuIdList());
        }
    }

    @Override
    @Cacheable
    public Role getById(Long id) {
        // ??????????????????
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.select(Role.Table.ID,Role.Table.ROLE_NAME,Role.Table.DESCRIPTION,Role.Table.SORT,Role.Table.COLOR,Role.Table.STATUS).eq(Role.Table.ID,id);
        Role role = roleMapper.selectOne(roleWrapper);
        // ????????????????????????
        QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.select(RoleMenu.Table.MENU_ID).eq(RoleMenu.Table.ROLE_ID,id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuWrapper);
        // ????????????ID??????
        List<Long> menuIdList = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(Role role){
        // ????????????????????????

        // ??????????????????
        roleMapper.updateById(role);
        //??????????????????
        QueryWrapper<RoleUser> wrapper = new QueryWrapper<>();
        wrapper.select(RoleUser.Table.USER_ID).eq(RoleUser.Table.ROLE_ID, role.getId());
        List<RoleUser> roleUsers = roleUserMapper.selectList(wrapper);
        for (RoleUser roleUser : roleUsers) {
            User user = new User();
            user.setId(roleUser.getUserId());
            user.setStatus(role.getStatus());
            userMapper.updateById(user);
        }
    }
}
