package com.chenjx.office.api.mapper;

import com.chenjx.office.api.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author chenjx
 * @description 针对表【tb_user(用户表)】的数据库操作Mapper
 * @createDate 2022-08-12 17:03:42
 * @Entity com.chenjx.office.api.entity.TbUser
 */
@Mapper
public interface TbUserMapper {

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




