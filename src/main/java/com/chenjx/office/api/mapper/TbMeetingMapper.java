package com.chenjx.office.api.mapper;

import com.chenjx.office.api.entity.TbMeeting;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_meeting(会议表)】的数据库操作Mapper
 * @createDate 2022-08-12 17:03:42
 * @Entity com.chenjx.office.api.entity.TbMeeting
 */
@Mapper
public interface TbMeetingMapper {
    ArrayList<HashMap> searchOfflineMeetingByPage(HashMap map);//多条件分页查询线下会议

    long searchOfflineMeetingCount(HashMap map);//多条件分页查询线下会议的总记录数

    int updateMeetingInstanceId(HashMap map);//更新工作流实例id

    int insert(TbMeeting meeting);//新增线下会议申请

    boolean searchMeetingMembersInSameDept(String uuid);//查询参会的人是否都属于一个部门

    ArrayList<HashMap> searchOfflineMeetingInWeek(HashMap map);//查询指定会议室一周的使用情况

    HashMap searchMeetingInfo(long id);//查询会议申请的详细信息

    HashMap searchCurrentMeetingInfo(long id);//查询当前会议的详细信息（包含出席和缺席）

    int deleteMeetingApplication(HashMap map);//删除会议业务数据

    HashMap searchMeetingById(HashMap map);
}




