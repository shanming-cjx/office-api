package com.chenjx.office.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenjx.office.api.entity.TbUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author chenjx
 * @description 针对表【tb_user(用户表)】的数据库操作Mapper
 * @createDate 2022-08-12 17:03:42
 * @Entity com.chenjx.office.api.entity.TbUser
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    Set<String> searchUserPermissions(int userId);

    Integer login(HashMap map);

    int updatePassword(HashMap map);

    ArrayList<HashMap> searchUserByPage(HashMap map);

    long searchUserCount(HashMap map);

    int insertUser(TbUser newUser);

    int updateUser(HashMap map);

    HashMap searchUserById(int id);

    int deleteUserByIds(Integer[] ids);

}




