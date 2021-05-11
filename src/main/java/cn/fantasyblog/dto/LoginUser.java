package cn.fantasyblog.dto;

import cn.fantasyblog.entity.Menu;
import cn.fantasyblog.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/3/31 21:11
 * Version 1.0
 **/

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends User implements UserDetails {

    private List<Menu> permissions;

    //应用内唯一的用户名
    //用户的加密后的密码， 不加密会使用{noop}前缀
    //用户的权限集， 默认需要添加ROLE_ 前缀
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.parallelStream()
                .map(p -> new SimpleGrantedAuthority(p.getAuthority()))
                .collect(Collectors.toSet());
    }

    //账户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否锁定
    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(this.getStatus(), Status.LOCKED);
    }

    //凭证是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //用户是否可用
    @Override
    public boolean isEnabled() {
        return Objects.equals(this.getStatus(), Status.ENABLED);
    }
}

interface Status {
    Integer LOCKED = -1;
    Integer ENABLED = 1;
}
