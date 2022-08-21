package com.chenjx.office.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.*;
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
            StpUtil.setLoginId(userId);//sa-token传入用户id登录
            Set<String> permissions = userService.searchUserPermissionsByUserId(userId);//封装用户（多个）权限成Set集合
            String token = StpUtil.getTokenInfo().getTokenValue();//获取登录用户的token
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

    @PostMapping("/update")
    @SaCheckPermission(value = {"ROOT", "USER:UPDATE"}, mode = SaMode.OR)
    @Operation(summary = "修改用户")
    public Resp update(@Valid @RequestBody UpdateUserRequest req) {
        HashMap param = JSONUtil.parse(req).toBean(HashMap.class);
        param.replace("role", JSONUtil.parseArray(req.getRole()).toString());
        int rows = userService.updateUser(param);
        if (rows == 1) {
            //修改资料后，把该用户踢下线
            StpUtil.logoutByLoginId(req.getUserId());
        }
        return Resp.ok().put("rows", rows);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找用户")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public Resp searchUserById(@Valid @RequestBody SearchUserByIdRequest req) {
        HashMap map = userService.searchUserById(req.getUserId());
        return Resp.ok(map);
    }

    @PostMapping("/deleteUserByIds")
    @SaCheckPermission(value = {"ROOT", "USER:DELETE"}, mode = SaMode.OR)
    @Operation(summary = "删除用户")
    public Resp deleteUserByIds(@Valid @RequestBody DeleteUserByIdsRequest req) {
        Integer userId = StpUtil.getLoginIdAsInt();
        if (ArrayUtil.contains(req.getIds(), userId)) {
            return Resp.error("您是管理员，不能删除自己的帐户");
        }
        int rows = userService.deleteUserByIds(req.getIds());
        if (rows > 0) {
            //把被删除的用户踢下线
            for (Integer id : req.getIds()) {
                StpUtil.logoutByLoginId(id);
            }
        }
        return Resp.ok().put("rows", rows);
    }


}
