package com.chenjx.office.api.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_dept】的数据库操作Service
 * @createDate 2022-08-12 17:03:42
 */
public interface TbDeptService {
    //查询所有部门
    ArrayList<HashMap> searchAllDept();
}
