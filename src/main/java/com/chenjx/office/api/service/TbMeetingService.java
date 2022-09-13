package com.chenjx.office.api.service;

import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbMeeting;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_meeting(会议表)】的数据库操作Service
 * @createDate 2022-08-12 17:03:42
 */
public interface TbMeetingService {
    //多条件分页查询所有线下会议
    PageUtils searchOfflineMeetingByPage(HashMap map);

    //新增线下会议申请
    int insert(TbMeeting meeting);

    //查询会议申请的详细信息
    ArrayList<HashMap> searchOfflineMeetingInWeek(HashMap map);

    //根据情况查询当前会议的详细信息（包含出席和缺席）
    HashMap searchMeetingInfo(short status, long id);
    //删除会议申请
    int deleteMeetingApplication(HashMap map);
}
