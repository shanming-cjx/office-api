package com.chenjx.office.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenjx.office.api.entity.TbUser;
import com.chenjx.office.api.service.TbUserService;
import com.chenjx.office.api.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;

/**
* @author chenjx
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2022-08-12 17:03:42
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{

    @Resource
    TbUserMapper userMapper;

    @Override
    public Integer login(HashMap map) {
        Integer userId = userMapper.login(map);
        return userId;
    }

    public Set<String> searchUserPermissionsByUserId(int userId) {
        Set<String> permissions = userMapper.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public int updatePassword(HashMap map) {
        int updatedRows = userMapper.updatePassword(map);
        return updatedRows;
    }
}




