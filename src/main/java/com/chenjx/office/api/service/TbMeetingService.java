package com.chenjx.office.api.service;

import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbMeeting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
* @author chenjx
* @description 针对表【tb_meeting(会议表)】的数据库操作Service
* @createDate 2022-08-12 17:03:42
*/
public interface TbMeetingService extends IService<TbMeeting> {
    public PageUtils searchOfflineMeetingByPage(HashMap param);
}
