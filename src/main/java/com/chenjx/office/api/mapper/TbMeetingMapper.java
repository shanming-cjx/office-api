package com.chenjx.office.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

public interface TbMeetingMapper extends BaseMapper<TbMeeting> {
    ArrayList<HashMap> searchOfflineMeetingByPage(HashMap param);

    long searchOfflineMeetingCount(HashMap param);
}




