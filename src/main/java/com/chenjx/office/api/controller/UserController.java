package com.chenjx.office.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.InsertUserRequest;
import com.chenjx.office.api.controller.request.LoginRequest;
import com.chenjx.office.api.controller.request.LogoutRequest;
import com.chenjx.office.api.controller.request.SearchUserByPageRequest;
import com.chenjx.office.api.entity.TbUser;
import com.chenjx.office.api.service.TbUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户web接口")
public class UserController {

    @Resource
    TbUserService userService;

    @PostMapping("/login")
    @Operation(summary = "登录校验")
    public Resp login(@Valid @RequestBody LoginRequest req) {
        HashMap requestParam = JSONUtil.parse(req).toBean(HashMap.class);
        Integer userId = userService.login(requestParam);
        Resp response = Resp.ok().put("result", userId != null);//userId不为空：result=true || 为空 result=false
        if (userId != null) {
            StpUtil.setLoginId(userId);
            Set<String> permissions = userService.searchUserPermissionsByUserId(userId);//封装用户（多个）权限成Set集合
            String token = StpUtil.getTokenInfo().getTokenValue();
            response.put("permissions", permissions).put("token", token);
        }
        return response;

    }

    @GetMapping("/logout")
    @SaCheckLogin//仅登录后访问
    @Operation(summary = "登出")
    public Resp Logout() {
        StpUtil.logout();//删除Redis的token
        return Resp.ok();
    }

    @PostMapping("/updatePassword")
    @SaCheckLogin//仅登录后访问
    @Operation(summary = "修改密码")
    public Resp updatePassword(@Valid @RequestBody LogoutRequest req) {
        int userId = StpUtil.getLoginIdAsInt();//将token里的userId提取出来
        HashMap map = new HashMap() {{//将查询条件userId和要修改的密码封装到参数map
            put("userId", userId);
            put("password", req.getPassword());
        }};
        int updateRows = userService.updatePasswordByUserId(map);
        StpUtil.logout();//删除Redis的token
        return Resp.ok().put("rows", updateRows);
    }

    @PostMapping("/searchUserByPage")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)//仅管理员和拥有查询权限的用户可以访问
    @Operation(summary = "用户多条件分页查询")
    public Resp searchUserByPage(@Valid @RequestBody SearchUserByPageRequest req) {
        int page = req.getPage();
        int length = req.getLength();
        int start = (page - 1) * length;
        HashMap param = JSONUtil.parse(req).toBean(HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = userService.searchUserByPage(param);
        return Resp.ok().put("pageData", pageUtils);
    }

    @PostMapping("/insert")
    @SaCheckPermission(value = {"ROOT", "USER:INSERT"}, mode = SaMode.OR)//仅ROOT或INSERT权限可访问
    @Operation(summary = "添加用户")
    public Resp insertUser(@Valid @RequestBody InsertUserRequest req) {
        TbUser user = JSONUtil.parse(req).toBean(TbUser.class);
        user.setStatus(1);
        user.setRole(JSONUtil.parseArray(req.getRole()).toString());
        user.setCreateTime(new Date());
        int rows = userService.insertUser(user);
        return Resp.ok().put("rows", rows);
    }

}
