package com.chenjx.office.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_meeting_room(会议室表)】的数据库操作Mapper
 * @createDate 2022-08-12 17:03:42
 * @Entity com.chenjx.office.api.entity.TbMeetingRoom
 */
@Mapper
public interface TbMeetingRoomMapper {

    ArrayList<HashMap> searchAllMeetingRoom();

    ArrayList<String> searchFreeMeetingRoom(HashMap map);
}




