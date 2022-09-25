package com.chenjx.office.api.service.impl;

import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.service.ApprovalService;
import com.chenjx.office.api.service.WorkFlowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Resource
    private WorkFlowService workFlowService;

    @Override
    public PageUtils searchTaskByPage(HashMap map) {
        //分页数据的转换
        int pageSize = (Integer) map.get("length");
        int startIndex = ((Integer) map.get("page") - 1) * pageSize;//偏移量
        PageUtils page = workFlowService.searchMyTasks(startIndex, pageSize, map);
        return page;
    }
}
