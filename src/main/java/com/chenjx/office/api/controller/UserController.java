package com.chenjx.office.api.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.redis.RedisCache;
import com.chenjx.office.api.common.util.JwtUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.*;
import com.chenjx.office.api.entity.TbUser;
import com.chenjx.office.api.entity.security.LoginUser;
import com.chenjx.office.api.service.TbUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户web接口")
public class UserController {

    @Resource
    TbUserService userService;
    @Resource
    private RedisCache redisCache;

    @PostMapping("/login")
    @Operation(summary = "登录校验")
    public Resp login(@Valid @RequestBody LoginRequest req) {
        HashMap requestParam = JSONUtil.parse(req).toBean(HashMap.class);
        LoginUser loginUser = userService.login(requestParam);
        String userId = loginUser.getUser().getId().toString();//获取userId
        String jwt = JwtUtil.createJWT(userId);//生成token
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);//"login:"+userId作为key，用户的信息作为value
        Resp response = Resp.ok().put("result", true).put("token",jwt).put("permissions",loginUser.getPermissions());
        return response;
    }

    @GetMapping("/logout")
    @Operation(summary = "登出")
    public Resp Logout() {
        LoginUser loginUser = userService.getUserByAuthentication();
        Integer userid = loginUser.getUser().getId();
        String message = redisCache.deleteObject("login:"+userid) ? "登出成功" : "登出失败";
        return Resp.ok().put("msg",message);
    }

    @PostMapping("/updatePassword")
    @Operation(summary = "修改密码")
    public Resp updatePassword(@Valid @RequestBody LogoutRequest req) {
        LoginUser loginUser = userService.getUserByAuthentication();
        Integer userId = loginUser.getUser().getId();
        HashMap map = new HashMap() {{//将查询条件userId和要修改的密码封装到参数map
            put("userId", userId);
            put("password", req.getPassword());
        }};
        int updateRows = userService.updatePasswordByUserId(map);
        boolean result = redisCache.deleteObject("login:"+userId);
        return Resp.ok().put("result",result).put("rows", updateRows);
    }

    @PostMapping("/searchUserByPage")
    @PreAuthorize("hasAnyAuthority('ROOT','USER:SELECT')")//仅管理员和拥有查询权限的用户可以访问
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
    @PreAuthorize("hasAnyAuthority('ROOT','USER:INSERT')")//仅ROOT或INSERT权限可访问
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
    @PreAuthorize("hasAnyAuthority('ROOT','USER:UPDATE')")//仅ROOT或UPDATE权限可访问
    @Operation(summary = "修改用户")
    public Resp update(@Valid @RequestBody UpdateUserRequest req) {
        HashMap param = JSONUtil.parse(req).toBean(HashMap.class);
        param.replace("role", JSONUtil.parseArray(req.getRole()).toString());
        int rows = userService.updateUser(param);
        if (rows == 1) {
            //修改资料后，把该用户踢下线
            redisCache.deleteObject("login:" + req.getUserId());
        }
        return Resp.ok().put("rows", rows);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找用户")
    @PreAuthorize("hasAnyAuthority('testqw')")//仅ROOT或SELECT权限可访问
    public Resp searchUserById(@Valid @RequestBody SearchUserByIdRequest req) {
        HashMap map = userService.searchUserById(req.getUserId());
        return Resp.ok(map);
    }

    @PostMapping("/deleteUserByIds")
    @PreAuthorize("hasAnyAuthority('ROOT','USER:DELETE')")//仅ROOT或DELETE权限可访问
    @Operation(summary = "删除用户")
    public Resp deleteUserByIds(@Valid @RequestBody DeleteUserByIdsRequest req) {
        LoginUser loginUser = userService.getUserByAuthentication();
        Integer userId = loginUser.getUser().getId();
        if (ArrayUtil.contains(req.getIds(), userId)) {
            return Resp.error("您是管理员，不能删除自己的帐户");
        }
        int rows = userService.deleteUserByIds(req.getIds());
        if (rows > 0) {
            //把被删除的用户踢下线
            for (Integer id : req.getIds()) {
                redisCache.deleteObject("login:" + userId);
            }
        }
        return Resp.ok().put("rows", rows);
    }


}
