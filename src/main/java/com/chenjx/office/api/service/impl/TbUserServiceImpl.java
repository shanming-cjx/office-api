package com.chenjx.office.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbUser;
import com.chenjx.office.api.mapper.TbUserMapper;
import com.chenjx.office.api.service.TbUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author chenjx
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2022-08-12 17:03:42
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
        implements TbUserService {

    @Resource
    TbUserMapper userMapper;

    @Override
    public Integer login(HashMap map) {//登录校验
        Integer userId = userMapper.login(map);
        return userId;
    }

    public Set<String> searchUserPermissionsByUserId(int userId) {//根据userId查用户权限
        Set<String> permissions = userMapper.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public int updatePasswordByUserId(HashMap map) {//根据userId修改密码
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
    public int insertUser(TbUser user) {
        int insertedRows = userMapper.insertUser(user);
        return insertedRows;
    }

    @Override
    public int updateUser(HashMap user) {
        int updatedRows = userMapper.updateUser(user);
        return updatedRows;
    }

    @Override
    public HashMap searchUserById(int userId) {
        return userMapper.searchUserById(userId);
    }

    @Override
    public int deleteUserByIds(Integer[] ids) {
        int rows = userMapper.deleteUserByIds(ids);
        return rows;

    }

}




