package com.chenjx.office.api.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.entity.TbMeeting;
import com.chenjx.office.api.entity.security.LoginUser;
import com.chenjx.office.api.exception.OfficeException;
import com.chenjx.office.api.mapper.TbMeetingMapper;
import com.chenjx.office.api.mapper.TbUserMapper;
import com.chenjx.office.api.service.TbMeetingService;
import com.chenjx.office.api.service.TbUserService;
import com.chenjx.office.api.service.WorkFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    public PageUtils searchOfflineMeetingByPage(HashMap map) {//分页查询所有线下会议
        ArrayList<HashMap> list = meetingMapper.searchOfflineMeetingByPage(map);
        long count = meetingMapper.searchOfflineMeetingCount(map);
        int start = (Integer) map.get("start");
        int length = (Integer) map.get("length");
        //把meeting字段转换成JSON数组对象
        for (HashMap pageMap : list) {
            String meeting = (String) pageMap.get("meeting");
            //如果Meeting是有效的字符串，就转换成JSON数组对象
            if (meeting != null && meeting.length() > 0) {
                pageMap.replace("meeting", JSONUtil.parseArray(meeting));
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
        LoginUser loginUser = userService.getLoginUserByAuthentication();
        Set<String> roles = loginUser.getRoles();//获取申请人（访问人）的角色列表
        Integer creatorId = loginUser.getUser().getId();//获取申请人（访问人）的id
        String creatorName = loginUser.getUser().getName();//获取申请人的真实姓名。
        //流程所需的变量"${identity=='总经理'}",${manager}部门经理username，"${result=='同意'&&sameDept==false}"跨部门&同意，${gm}总经理username
        Map<String, Object> processValue = new HashMap<>();//流程变量
        if (!roles.contains("总经理")) {//判断申请人是否为总经理
            processValue.put("identity", "");
            //查询部门经理username
            String managerUserName = userMapper.searchDeptManagerUserName(creatorId);
            processValue.put("manager", managerUserName);
            //查询总经理username
            String gmUserName = userMapper.searchGmUserName();
            processValue.put("gm", gmUserName);
            //查询参会人是否为同一个部门
            boolean bool = meetingMapper.searchMeetingMembersInSameDept(meeting.getUuid());
            processValue.put("sameDept", bool+"");
        } else {
            processValue.put("identity", "总经理");
        }
        //将简要的业务信息保存到流程变量，避免多表重复查找
        processValue.put("title",meeting.getTitle());
        processValue.put("type","会议申请");
        processValue.put("status","待审批");
        processValue.put("creatorId",creatorId);
        processValue.put("creatorName",creatorName);
        processValue.put("createDate",DateUtil.today());
        //开启流程实例
        workFlowService.startInstance("meeting", meeting.getTitle(), meeting.getUuid(), processValue);
        //TODO quartZ的时间要精确到秒
        //meetingWorkflowTask.startMeetingWorkflow(meeting.getUuid(), meeting.getCreatorId(), meeting.getTitle(),
        //                meeting.getDate(), meeting.getStart() + ":00", meeting.getType() == 1 ? "线上会议" : "线下会议");
        return rows;
    }

    @Override
    public ArrayList<HashMap> searchOfflineMeetingInWeek(HashMap map) {
        ArrayList<HashMap> list = meetingMapper.searchOfflineMeetingInWeek(map);
        return list;
    }


    @Override
    public HashMap searchMeetingInfo(short status, long id) {
        //判断正在进行中的会议
        HashMap map;
        if (status == 4 || status == 5) {
            //查询当前会议的详细信息（包含出席和缺席）
            map = meetingMapper.searchCurrentMeetingInfo(id);
        } else {
            //查询会议申请的详细信息
            map = meetingMapper.searchMeetingInfo(id);
        }
        return map;
    }

    @Override
    public int deleteMeetingApplication(HashMap map) {
//        Long id = MapUtil.getLong(map, "id");
//        String uuid = MapUtil.getStr(map, "uuid");
        String instanceId = MapUtil.getStr(map, "instanceId");
        //查询会议详情，判断是否距离会议开始不足20分钟
        HashMap meeting = meetingMapper.searchMeetingById(map);
        String date = MapUtil.getStr(meeting, "date");
        String start = MapUtil.getStr(meeting, "start");
        int status = MapUtil.getInt(meeting, "status");
        boolean isCreator = Boolean.parseBoolean(MapUtil.getStr(meeting, "isCreator"));
        DateTime dateTime = DateUtil.parse(date + " " + start);
        DateTime now = DateUtil.date();
        if (now.isAfterOrEquals(dateTime.offset(DateField.MINUTE, -20))) {
            throw new OfficeException("距离会议开始不足20分钟，不能删除会议");
        }
        //只能申请人删除该会议
        if (!isCreator) {
            throw new OfficeException("只能申请人删除该会议");
        }
        //待审批和未开始的会议可以删除
        if (status == 1 || status == 3) {
            int rows = meetingMapper.deleteMeetingApplication(map);
            if (rows == 1) {
                String reason = MapUtil.getStr(map, "reason");
                //TODO Activiti7删除流程实例是否会删除历史记录。
                //meetingWorkflowTask.deleteMeetingApplication(uuid, instanceId, reason);
                workFlowService.deleteInstance(instanceId,reason);
            }
            return rows;
        } else {
            throw new OfficeException("只能删除待审批和未开始的会议");
        }
    }
}




