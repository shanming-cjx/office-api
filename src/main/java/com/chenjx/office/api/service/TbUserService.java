package com.chenjx.office.api.service;

import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Set;

/**
* @author chenjx
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2022-08-12 17:03:42
*/
public interface TbUserService extends IService<TbUser> {
    //登录校验
    Integer login(HashMap map);
    //根据userId查用户权限
    Set<String> searchUserPermissionsByUserId(int userId);
    //根据userId修改密码
    int updatePasswordByUserId(HashMap map);
    //多条件分页查询
    PageUtils searchUserByPage(HashMap map);

    int insertUser(TbUser user);
    int updateUser(HashMap user);
}
