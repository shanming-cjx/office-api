package com.chenjx.office.api.controller;

import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.service.TbRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/role")
@Tag(name = "RoleController", description = "用户角色web接口")
public class RoleController {

    @Resource
    TbRoleService roleService;

    @GetMapping("/searchAllRole")
    @Operation(summary = "查询所有角色")
    public Resp searchAllRole(){
        ArrayList<HashMap> list = roleService.searchAllRole();
        return Resp.ok().put("list", list);
    }
}
