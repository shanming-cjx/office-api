package com.chenjx.office.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenjx.office.api.entity.TbDept;
import com.chenjx.office.api.service.TbDeptService;
import com.chenjx.office.api.mapper.TbDeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
* @author chenjx
* @description 针对表【tb_dept】的数据库操作Service实现
* @createDate 2022-08-12 17:03:42
*/
@Service
public class TbDeptServiceImpl extends ServiceImpl<TbDeptMapper, TbDept>
    implements TbDeptService{

    @Resource
    TbDeptMapper deptMapper;

    @Override
    public ArrayList<HashMap> searchAllDept() {
        return deptMapper.searchAllDept();
    }
}




