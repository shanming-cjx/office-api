package com.chenjx.office.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbMeeting;
import com.chenjx.office.api.exception.OfficeException;
import com.chenjx.office.api.mapper.TbMeetingMapper;
import com.chenjx.office.api.mapper.TbUserMapper;
import com.chenjx.office.api.service.TbMeetingService;
import com.chenjx.office.api.service.TbUserService;
import com.chenjx.office.api.service.WorkFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chenjx
 * @description 针对表【tb_meeting(会议表)】的数据库操作Service实现
 * @createDate 2022-08-12 17:03:42
 */
@Service
@Slf4j
public class TbMeetingServiceImpl implements TbMeetingService {

    @Resource
    private TbMeetingMapper meetingMapper;
    @Resource
    private WorkFlowService workFlowService;
    @Resource
    private TbUserService userService;
    @Resource
    private TbUserMapper userMapper;

    @Override
    public PageUtils searchOfflineMeetingByPage(HashMap param) {//分页查询所有线下会议
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

    @Override
    public int insert(TbMeeting meeting) {//新增线下会议申请
        int rows = meetingMapper.insert(meeting);//新增流程业务数据
        if (rows != 1) {
            throw new OfficeException("会议添加失败");
        }
        //工作流所需数据
        Set<String> roles = userService.getLoginUserByAuthentication().getRoles();//获取申请人（访问人）的角色列表
        Integer creatorId = userService.getLoginUserByAuthentication().getUser().getId();//获取申请人（访问人）的id
        //流程所需的变量"${identity=='总经理'}",${manager}部门经理username，"${result=='同意'&&sameDept==false}"跨部门&同意，${gm}总经理username
        Map<String,Object> processValue = new HashMap<>();//流程变量
        if (!roles.contains("总经理")){//判断申请人是否为总经理
            processValue.put("identity", "");
            //查询部门经理username
            String managerUserName = userMapper.searchDeptManagerUserName(creatorId);
            processValue.put("manager", managerUserName);
            //查询总经理username
            String gmUserName = userMapper.searchGmUserName();
            processValue.put("gm", gmUserName);
            //查询参会人是否为同一个部门
            boolean bool = meetingMapper.searchMeetingMembersInSameDept(meeting.getUuid());
            processValue.put("sameDept", bool);
        }else {
            processValue.put("identity", "总经理");
        }
        //开启流程实例
        workFlowService.startInstance("meeting",meeting.getTitle(),meeting.getUuid(),processValue);
        //TODO quartZ的时间要精确到秒
        //meetingWorkflowTask.startMeetingWorkflow(meeting.getUuid(), meeting.getCreatorId(), meeting.getTitle(),
        //                meeting.getDate(), meeting.getStart() + ":00", meeting.getType() == 1 ? "线上会议" : "线下会议");
        return rows;
    }
}




