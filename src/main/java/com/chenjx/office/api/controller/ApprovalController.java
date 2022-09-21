package com.chenjx.office.api.controller;

import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.SearchTaskByPageRequest;
import com.chenjx.office.api.entity.security.LoginUser;
import com.chenjx.office.api.service.ApprovalService;
import com.chenjx.office.api.service.TbUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/approval")
@Tag(name = "ApprovalController", description = "任务审批Web接口")
@Slf4j
public class ApprovalController {

    @Resource
    private TbUserService userService;
    @Resource
    private ApprovalService approvalService;

    @PostMapping("/searchTaskByPage")
    @Operation(summary = "查询分页任务列表")
    @PreAuthorize("hasAnyAuthority('WORKFLOW:APPROVAL','FILE:ARCHIVE')")
    public Resp searchTaskByPage(@Valid @RequestBody SearchTaskByPageRequest req) {
        HashMap map = JSONUtil.parse(req).toBean(HashMap.class);
        LoginUser loginUserInfo = userService.getLoginUserByAuthentication();
        map.put("userId", loginUserInfo.getUser().getId());
        map.put("userName", loginUserInfo.getUsername());
//        map.put("role", loginUserInfo.getRoles());
        PageUtils pageUtils = approvalService.searchTaskByPage(map);
        return Resp.ok().put("page", pageUtils);
    }
}
