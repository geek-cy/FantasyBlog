package cn.fantasyblog.service.impl;

import cn.fantasyblog.dao.RoleMenuMapper;
import cn.fantasyblog.entity.Role;
import cn.fantasyblog.entity.RoleMenu;
import cn.fantasyblog.entity.RoleUser;
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
    RoleMapper roleMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Autowired
    RoleUserMapper roleUserMapper;

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
            throw new BadRequestException("该角色存在关联用户，无法删除");
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
                throw new BadRequestException("角色"+role+"存在关联用户，无法删除");
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
                throw new BadRequestException("角色名称已存在");
            }
            // 保存角色
            roleMapper.insert(role);
            // 保存角色权限
            roleMenuMapper.insertBatch(role.getId(),role.getMenuIdList());
        } else {
            List<Role> roles = roleMapper.selectList(wrapper);
            roles = roles.stream().filter(r->!r.getId().equals(role.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(roles)){
                throw new BadRequestException("角色名称已存在");
            }
            // 更新角色
            roleMapper.updateById(role);
            // 更新角色权限
            QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
            roleMenuWrapper.eq(RoleMenu.Table.ROLE_ID,role.getId());
            // 删除原有角色权限
            roleMenuMapper.delete(roleMenuWrapper);
            //再添加新的角色权限
            roleMenuMapper.insertBatch(role.getId(), role.getMenuIdList());
        }
    }

    @Override
    @Cacheable
    public Role getById(Long id) {
        // 查询角色信息
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.select(Role.Table.ID,Role.Table.ROLE_NAME,Role.Table.DESCRIPTION,Role.Table.RANK,Role.Table.COLOR,Role.Table.STATUS).eq(Role.Table.ID,id);
        Role role = roleMapper.selectOne(roleWrapper);
        // 查询角色权限信息
        QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.select(RoleMenu.Table.MENU_ID).eq(RoleMenu.Table.ROLE_ID,id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuWrapper);
        // 查出菜单ID链表
        List<Long> menuIdList = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        role.setMenuIdList(menuIdList);
        return role;
    }
}
