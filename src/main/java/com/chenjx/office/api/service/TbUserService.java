package com.chenjx.office.api.service;

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
    Integer login(HashMap map);
    Set<String> searchUserPermissionsByUserId(int userId);
    int updatePassword(HashMap map);
}
