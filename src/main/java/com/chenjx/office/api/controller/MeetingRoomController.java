package com.chenjx.office.api.controller;

import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.SearchFreeMeetingRoomRequest;
import com.chenjx.office.api.service.TbMeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/meeting_room")
@Tag(name = "MeetingRoomController", description = "会议管理Web接口")
public class MeetingRoomController {
    @Resource
    private TbMeetingRoomService meetingRoomService;

    @GetMapping("/searchAllMeetingRoom")
    @Operation(summary = "查询所有会议室")
    public Resp searchAllMeetingRoom() {
        ArrayList<HashMap> list = meetingRoomService.searchAllMeetingRoom();
        return Resp.ok().put("list", list);
    }

    @PostMapping("/searchFreeMeetingRoom")
    @Operation(summary = "查询空闲会议室")
    public Resp searchFreeMeetingRoom(@Valid @RequestBody SearchFreeMeetingRoomRequest req) {
        HashMap param = JSONUtil.parse(req).toBean(HashMap.class);
//        param.replace("start",param.get("start")+":00");
//        param.replace("end",param.get("end")+":00");
        ArrayList<String> list = meetingRoomService.searchFreeMeetingRoom(param);
        return Resp.ok().put("list", list);
    }
}
