package com.chenjx.office.api.controller;

import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.service.TbDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/dept")
@Tag(name = "DeptController", description = "用户部门web接口")
public class DeptController {

    @Resource
    TbDeptService deptService;

    @GetMapping("/searchAllDept")
    @Operation(summary = "查询所有部门")
    public Resp searchAllRole(){
        ArrayList<HashMap> list = deptService.searchAllDept();
        return Resp.ok().put("list", list);
    }
}
