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
public interface TbUserMapper {//TODO 密码加密解密改装


    Set<String> searchUserPermissions(int userId);//根据userId查用户权限

//    Integer login(HashMap map);//登录校验

    int updatePassword(HashMap map);//根据userId修改密码

    ArrayList<HashMap> searchUserByPage(HashMap map);//多条件分页查询用户信息

    long searchUserCount(HashMap map);//多条件分页查询用户信息的总条数

    int insertUser(TbUser newUser);//新增用户

    int updateUser(HashMap map);//修改用户信息

    HashMap searchUserById(int id);//根据用户id查用户信息

    TbUser searchUserByUserName(String userName);//根据用户名查用户信息

    int deleteUserByIds(Integer[] ids);//根据用户id删除用户



}




