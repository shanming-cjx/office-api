package com.chenjx.office.api.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.InsertMeetingRequest;
import com.chenjx.office.api.controller.request.SearchOfflineMeetingByPageRequest;
import com.chenjx.office.api.entity.TbMeeting;
import com.chenjx.office.api.service.TbMeetingService;
import com.chenjx.office.api.service.TbUserService;
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

    @Resource
    private TbUserService userService;

    @PostMapping("/searchOfflineMeetingByPage")
    @Operation(summary = "查询线下会议的分页数据")
    public Resp searchOfflineMeetingByPage(@Valid @RequestBody SearchOfflineMeetingByPageRequest req) {
        int page = req.getPage();
        int length = req.getLength();
        int start = (page - 1) * length;
        HashMap param = new HashMap() {{
            put("date", req.getDate());
            put("mold", req.getMold());
            put("userId", userService.getLoginUserByAuthentication().getUser().getId());
            put("start", start);
            put("length", length);
        }};
        PageUtils pageUtils = meetingService.searchOfflineMeetingByPage(param);
        return Resp.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "申请会议")
    public Resp insert(@Valid @RequestBody InsertMeetingRequest req) {
        DateTime start = DateUtil.parse(req.getDate() + " " + req.getStart());
        DateTime end = DateUtil.parse(req.getDate() + " " + req.getEnd());
        if (start.isAfterOrEquals(end)) {
            return Resp.error("结束时间必须大于开始时间");
        } else if (new DateTime().isAfterOrEquals(start)) {
            return Resp.error("会议开始时间不能早于当前时间");
        }
        TbMeeting meeting = JSONUtil.parse(req).toBean(TbMeeting.class);
        meeting.setUuid(UUID.randomUUID().toString(true));//生成uuId可做businessId
        meeting.setCreatorId((long) userService.getLoginUserByAuthentication().getUser().getId());//获取security过滤链中的用户信息。
        meeting.setStatus(1);//“1”表示开始申请还未审批
        int rows = meetingService.insert(meeting);
        return Resp.ok().put("rows", rows);
    }
}
