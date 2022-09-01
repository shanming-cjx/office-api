package com.chenjx.office.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_role(角色表)】的数据库操作Mapper
 * @createDate 2022-08-12 17:03:42
 * @Entity com.chenjx.office.api.entity.TbRole
 */
@Mapper
public interface TbRoleMapper {
    ArrayList<HashMap> searchAllRole();
}




