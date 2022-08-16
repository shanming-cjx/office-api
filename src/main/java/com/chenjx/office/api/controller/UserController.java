package com.chenjx.office.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.LoginRequest;
import com.chenjx.office.api.controller.request.LogoutRequest;
import com.chenjx.office.api.service.impl.TbUserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户web接口")
public class UserController {

    @Resource
    TbUserServiceImpl userService;

    @PostMapping("/login")
    @Operation(summary = "登录校验")
    public Resp login(@Valid @RequestBody LoginRequest req) {
        HashMap requestParam = JSONUtil.parse(req).toBean(HashMap.class);
        Integer userId = userService.login(requestParam);
        Resp response = Resp.ok().put("result", userId != null);//userId不为空：result=true || 为空 result=false
        if (userId != null){
            StpUtil.setLoginId(userId);
            Set<String> permissions = userService.searchUserPermissionsByUserId(userId);//封装用户（多个）权限成Set集合
            String token=StpUtil.getTokenInfo().getTokenValue();
            response.put("permissions",permissions).put("token",token);
        }
        return response;

    }

    @GetMapping("/logout")
    @SaCheckLogin
    @Operation(summary = "登出")
    public Resp Logout(){
        StpUtil.logout();//删除Redis的token
        return Resp.ok();
    }


    @PostMapping("/updatePassword")
    @SaCheckLogin
    @Operation(summary = "修改密码")
    public Resp updatePassword(@Valid @RequestBody LogoutRequest req){
        int userId = StpUtil.getLoginIdAsInt();//将token里的userId提取出来
        HashMap map = new HashMap(){{//将查询条件userId和要修改的密码封装到参数map
           put("userId",userId);
           put("password",req.getPassword());
        }};
        int updateRows = userService.updatePassword(map);
        StpUtil.logout();//删除Redis的token
        return Resp.ok().put("rows",updateRows);
    }

}
