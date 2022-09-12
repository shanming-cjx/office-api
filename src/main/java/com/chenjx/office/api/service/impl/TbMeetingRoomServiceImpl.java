package com.chenjx.office.api.service.impl;

import com.chenjx.office.api.mapper.TbMeetingRoomMapper;
import com.chenjx.office.api.service.TbMeetingRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_meeting_room(会议室表)】的数据库操作Service实现
 * @createDate 2022-08-12 17:03:42
 */
@Service
public class TbMeetingRoomServiceImpl implements TbMeetingRoomService {
    @Resource
    private TbMeetingRoomMapper meetingRoomMapper;

    @Override
    public ArrayList<HashMap> searchAllMeetingRoom() {
        ArrayList<HashMap> list = meetingRoomMapper.searchAllMeetingRoom();
        return list;
    }

    @Override
    public ArrayList<String> searchFreeMeetingRoom(HashMap param) {
        ArrayList<String> list = meetingRoomMapper.searchFreeMeetingRoom(param);
        return list;
    }
}




