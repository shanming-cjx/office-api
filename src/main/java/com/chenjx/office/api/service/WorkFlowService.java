package com.chenjx.office.api.service;

import com.chenjx.office.api.common.util.PageUtils;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjx
 * @description 工作流服务接口
 * @createDate 2022-09-05
 */
public interface WorkFlowService {
    //流程部署
    boolean deployment(String filePath, String deploymentName);

    //TODO 获取流程定义,新特性只有定义，没有部署ID，可以根据定义判断部署,但没有部署时添加的名字。所以综合考虑还是手写sql
    //启动流程实例
    String startInstance(String definitionKey, String instanceName, String businessKey, Map<String, Object> instanceValue);

    //多条件查询我的待办任务
    PageUtils searchMyTasks(int startIndex, int pageSize, HashMap map);

    //多条件查询待办任务
    PageUtils searchTasks(String userName, String creatorName, String type, String status, String instanceId, int page, int index);

    //完成我的待办任务
    boolean completeTask(String taskId, Map<String, Object> variables);

    //查询所有流程实例，即已经开启的流程
    Page<ProcessInstance> searchAllInstances(int startIndex, int pageSize);

    //删除流程实例
    boolean deleteInstance(String instanceId, String reason);


}
