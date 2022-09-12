package com.chenjx.office.api.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_meeting_room(会议室表)】的数据库操作Service
 * @createDate 2022-08-12 17:03:42
 */
public interface TbMeetingRoomService {
    //查询所有会议室
    ArrayList<HashMap> searchAllMeetingRoom();
    //查询空闲的会议室
    ArrayList<String> searchFreeMeetingRoom(HashMap param);
}
