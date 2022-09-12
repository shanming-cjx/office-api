package com.chenjx.office.api.service.impl;

import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbUser;
import com.chenjx.office.api.entity.security.LoginUser;
import com.chenjx.office.api.mapper.TbUserMapper;
import com.chenjx.office.api.service.TbUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * @author chenjx
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2022-08-12 17:03:42
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    TbUserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder bCryptEncoder;

    @Override
    public LoginUser login(HashMap map) {//登录校验
        UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(map.get("userName"),map.get("password"));
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        if(Objects.isNull(authenticate)){//不会触发，由于登录验证失败时Security的过滤链中会抛出异常，直接进行异常处理；这里是为了便于后期维护阅读
//            throw new RuntimeException("用户名或密码错误");
//        }
        return (LoginUser) authenticate.getPrincipal();
    }

    public Set<String> searchUserPermissionsByUserId(int userId) {//根据userId查用户权限
        Set<String> permissions = userMapper.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public int updatePasswordByUserId(HashMap map) {//根据userId修改密码
        map.replace("password",bCryptEncoder.encode(map.get("password").toString()));//密码加密
        int updatedRows = userMapper.updatePassword(map);
        return updatedRows;
    }

    @Override
    public PageUtils searchUserByPage(HashMap map) {//多条件分页查询
        ArrayList<HashMap> list = userMapper.searchUserByPage(map);
        long count = userMapper.searchUserCount(map);
        int pageIndex = (Integer) map.get("page");
        int pageSize = (Integer) map.get("length");
        return new PageUtils(list, count, pageIndex, pageSize);
    }

    @Override
    public int insertUser(TbUser user) {//新增用户
        user.setPassword(bCryptEncoder.encode(user.getPassword()));//密码加密
        int insertedRows = userMapper.insertUser(user);
        return insertedRows;
    }

    @Override
    public int updateUser(HashMap user) {//修改用户信息
        if(Objects.isNull(user.get("password")))
            user.replace("password",bCryptEncoder.encode(user.get("password").toString()));//密码加密
        int updatedRows = userMapper.updateUser(user);
        return updatedRows;
    }

    @Override
    public HashMap searchUserById(int userId) {//根据id查询用户信息
        return userMapper.searchUserById(userId);
    }

    @Override
    public int deleteUserByIds(Integer[] ids) {//删除非管理员用户
        int rows = userMapper.deleteUserByIds(ids);
        return rows;
    }

    @Override
    public LoginUser getLoginUserByAuthentication() {//从security过滤链中获取用户信息，而security除了登录是从Mysql里获取用户信息，其余每次前端请求都是从redis中获取
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//从SecurityContextHolder中获取authentication
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();//再从authentication中获取之前从数据库（mysql或redis）封入的对象loginUser
        return loginUser;
    }

    @Override
    public ArrayList<HashMap> searchAllUser() {
        ArrayList<HashMap> list = userMapper.searchAllUser();
        return list;
    }
}




