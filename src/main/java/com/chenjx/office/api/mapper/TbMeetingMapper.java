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
    ArrayList<HashMap> searchOfflineMeetingByPage(HashMap param);//多条件分页查询线下会议

    long searchOfflineMeetingCount(HashMap param);//多条件分页查询线下会议的总记录数

    int updateMeetingInstanceId(HashMap param);//更新工作流实例id

    int insert(TbMeeting meeting);//新增线下会议申请

    boolean searchMeetingMembersInSameDept( String uuid);//查询参会的人是否都属于一个部门
}




