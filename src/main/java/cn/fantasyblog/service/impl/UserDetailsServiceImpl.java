package cn.fantasyblog.service.impl;

import cn.fantasyblog.dao.MenuMapper;
import cn.fantasyblog.dao.UserMapper;
import cn.fantasyblog.entity.Menu;
import cn.fantasyblog.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.fantasyblog.dto.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-20 20:11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.Table.USERNAME,username);
        User user = userMapper.selectOne(wrapper);
        if(StringUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        LoginUser loginUser = new LoginUser();
        // 将源对象的属性拷贝到目标对象
        BeanUtils.copyProperties(user,loginUser);
        // 查询用户权限
        List<Menu> permission = menuMapper.listPermissionByUserId(loginUser.getId());
        // 过滤空权限
        permission = permission.stream().filter(p -> (!StringUtils.isEmpty(p.getAuthority()))).collect(Collectors.toList());
        loginUser.setPermissions(permission);
        return loginUser;
    }
}
