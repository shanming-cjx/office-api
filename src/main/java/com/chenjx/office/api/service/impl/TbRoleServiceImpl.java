package com.chenjx.office.api.service.impl;

import com.chenjx.office.api.mapper.TbRoleMapper;
import com.chenjx.office.api.service.TbRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_role(角色表)】的数据库操作Service实现
 * @createDate 2022-08-12 17:03:42
 */
@Service
public class TbRoleServiceImpl implements TbRoleService {

    @Resource
    TbRoleMapper roleMapper;

    @Override
    public ArrayList<HashMap> searchAllRole() {//查询所有角色
        return roleMapper.searchAllRole();
    }
}




