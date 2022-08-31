package com.chenjx.office.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbMeeting;
import com.chenjx.office.api.mapper.TbMeetingMapper;
import com.chenjx.office.api.service.TbMeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenjx
 * @description 针对表【tb_meeting(会议表)】的数据库操作Service实现
 * @createDate 2022-08-12 17:03:42
 */
@Service
@Slf4j
public class TbMeetingServiceImpl extends ServiceImpl<TbMeetingMapper, TbMeeting>
        implements TbMeetingService {

    @Resource
    private TbMeetingMapper meetingMapper;

    @Override
    public PageUtils searchOfflineMeetingByPage(HashMap param) {
        ArrayList<HashMap> list = meetingMapper.searchOfflineMeetingByPage(param);
        long count = meetingMapper.searchOfflineMeetingCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        //把meeting字段转换成JSON数组对象
        for (HashMap map : list) {
            String meeting = (String) map.get("meeting");
            //如果Meeting是有效的字符串，就转换成JSON数组对象
            if (meeting != null && meeting.length() > 0) {
                map.replace("meeting", JSONUtil.parseArray(meeting));
            }
        }
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }
}




