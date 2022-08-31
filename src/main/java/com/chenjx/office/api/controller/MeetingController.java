package com.chenjx.office.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.SearchOfflineMeetingByPageRequest;
import com.chenjx.office.api.service.TbMeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/meeting")
@Tag(name = "MeetingController", description = "会议接口")
@Slf4j
public class MeetingController {

    @Resource
    TbMeetingService meetingService;

    @PostMapping("/searchOfflineMeetingByPage")
    @Operation(summary = "查询线下会议的分页数据")
    @SaCheckLogin
    public Resp searchOfflineMeetingByPage(@Valid @RequestBody SearchOfflineMeetingByPageRequest req) {
        int page = req.getPage();
        int length = req.getLength();
        int start = (page - 1) * length;
        HashMap param = new HashMap() {{
            put("date", req.getDate());
            put("mold", req.getMold());
            put("userId", StpUtil.getLoginId());
            put("start", start);
            put("length", length);
        }};
        PageUtils pageUtils = meetingService.searchOfflineMeetingByPage(param);
        return Resp.ok().put("page", pageUtils);
    }
}
