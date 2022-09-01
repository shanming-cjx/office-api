package com.chenjx.office.api.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_role(角色表)】的数据库操作Service
 * @createDate 2022-08-12 17:03:42
 */
public interface TbRoleService {
    //查询所有角色
    ArrayList<HashMap> searchAllRole();
}
