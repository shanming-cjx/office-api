package com.chenjx.office.api.entity.security;

import com.chenjx.office.api.entity.TbUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *Spring Security登录验证所需的用户信息，改为从数据库查询
 */


@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private TbUser user;

    //存储权限信息(通用)
    private Set<String> permissions;
    //存储SpringSecurity所需要的权限信息的集合
//    @JSONField(serialize = false)
    private Set<GrantedAuthority> authorities;

    public LoginUser(TbUser user, Set<String> permissions) {
        this.user = user;
        this.permissions = permissions;
        this.getAuthorities();
    }

    public LoginUser(TbUser user, Set<String> permissions, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.permissions = permissions;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(MyGrantedAuthority::new)
                .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}