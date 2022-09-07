package com.chenjx.office.api.service.impl.security;

import com.chenjx.office.api.entity.TbUser;
import com.chenjx.office.api.entity.security.LoginUser;
import com.chenjx.office.api.mapper.TbUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private TbUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        TbUser user = userMapper.searchUserByUserName(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名错误");
        }
        //根据用户查询权限信息 添加到LoginUser中
        Set<String> permissions = userMapper.searchUserPermissions(user.getId());
        permissions.add("ROLE_ACTIVITI_USER");//activiti7所需的权限
        Set<String> roles = userMapper.searchUserRoles(user.getId());
        //封装成UserDetails对象返回 
        return new LoginUser(user,permissions,roles);
    }
}