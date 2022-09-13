package com.chenjx.office.api.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.InsertMeetingRequest;
import com.chenjx.office.api.controller.request.SearchMeetingInfoRequest;
import com.chenjx.office.api.controller.request.SearchOfflineMeetingByPageRequest;
import com.chenjx.office.api.controller.request.SearchOfflineMeetingInWeekRequest;
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
import java.util.ArrayList;
import java.util.Date;
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

    @PostMapping("/searchOfflineMeetingInWeek")
    @Operation(summary = "查询某个会议室一周的会议")
    public Resp searchOfflineMeetingInWeek(@Valid @RequestBody SearchOfflineMeetingInWeekRequest req) {
        String date = req.getDate();
        DateTime startDate, endDate;
        //判断是否有选择从那天开始查询周日历
        if (date != null && date.length() > 0) {
            startDate=DateUtil.parseDate(date);
            endDate=startDate.offsetNew(DateField.DAY_OF_WEEK,6);
        }else{//否则以现在的时间所在的周为查询范围
            startDate=DateUtil.beginOfWeek(new Date());
            endDate=DateUtil.endOfWeek(new Date());
        }
        HashMap param = new HashMap() {{
            put("place", req.getName());
            put("startDate", startDate.toDateStr());
            put("endDate", endDate.toDateStr());
            put("mold", req.getMold());
            put("userId", (long)userService.getLoginUserByAuthentication().getUser().getId());
        }};
        ArrayList<HashMap> list=meetingService.searchOfflineMeetingInWeek(param);
        ArrayList days=new ArrayList();
        //利用hutool工具类创建周信息
        DateRange range=DateUtil.range(startDate,endDate, DateField.DAY_OF_WEEK);
        range.forEach(one->{
            JSONObject json=new JSONObject();
            json.set("date",one.toString("MM/dd"));
            json.set("day",one.dayOfWeekEnum().toChinese("周"));
            days.add(json);
        });
        return Resp.ok().put("list",list).put("days",days);
    }

    @PostMapping("/searchMeetingInfo")
    @Operation(summary = "查询会议信息")
    public Resp searchMeetingInfo(@Valid @RequestBody SearchMeetingInfoRequest req) {
        HashMap map = meetingService.searchMeetingInfo(req.getStatus(), req.getId());
        return Resp.ok(map);
    }
}
