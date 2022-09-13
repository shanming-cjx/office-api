package com.chenjx.office.api.service;

import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbUser;
import com.chenjx.office.api.entity.security.LoginUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author chenjx
 * @description 针对表【tb_user(用户表)】的数据库操作Service
 * @createDate 2022-08-12 17:03:42
 */
public interface TbUserService {
    //登录校验
    LoginUser login(HashMap map);

    //根据userId查用户权限
    Set<String> searchUserPermissionsByUserId(int userId);

    //根据userId修改密码
    int updatePasswordByUserId(HashMap map);

    //根据用户id查询用户基本信息
    HashMap searchUserSummary(int userId);

    //多条件分页查询
    PageUtils searchUserByPage(HashMap map);

    //新增用户
    int insertUser(TbUser user);

    //修改用户信息
    int updateUser(HashMap user);

    //根据id查询用户信息
    HashMap searchUserById(int userId);

    //删除非管理员用户
    int deleteUserByIds(Integer[] ids);

    //从SecurityContextHolder中获取authentication中的用户信息
    LoginUser getLoginUserByAuthentication();

    //查询所有用户
    ArrayList<HashMap> searchAllUser();
}
