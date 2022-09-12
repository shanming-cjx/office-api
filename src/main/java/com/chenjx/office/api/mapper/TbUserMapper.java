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

    //根据userId查用户权限
    Set<String> searchUserPermissions(int userId);

    //根据userId查用户角色
    Set<String> searchUserRoles(int userId);

    //根据userId查用户的部门经理username
    String searchDeptManagerUserName(int userId);

    //查询总经理的username
    String searchGmUserName();

    //登录校验
//    Integer login(HashMap map);
    //根据userId修改密码
    int updatePassword(HashMap map);

    //多条件分页查询用户信息
    ArrayList<HashMap> searchUserByPage(HashMap map);

    //多条件分页查询用户信息的总条数
    long searchUserCount(HashMap map);

    //新增用户
    int insertUser(TbUser newUser);

    //修改用户信息
    int updateUser(HashMap map);

    //根据用户id查用户信息
    HashMap searchUserById(int id);

    //根据用户名查用户信息
    TbUser searchUserByUserName(String userName);

    //根据用户id删除用户
    int deleteUserByIds(Integer[] ids);

    //查询所有在职用户
    ArrayList<HashMap> searchAllUser();
}




