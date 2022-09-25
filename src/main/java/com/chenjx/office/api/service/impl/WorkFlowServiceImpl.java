package com.chenjx.office.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.PageUtils;
import com.chenjx.office.api.exception.WorkflowException;
import com.chenjx.office.api.mapper.ApprovalMapper;
import com.chenjx.office.api.service.WorkFlowService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WorkFlowServiceImpl implements WorkFlowService {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessRuntime processRuntime;
    @Resource
    private TaskRuntime taskRuntime;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private ApprovalMapper approvalMapper;

    @Override
    public boolean deployment(String filePath, String deploymentName) {//流程部署
//        Deployment deployment =
        repositoryService.createDeployment()
                .addClasspathResource(filePath)
                .name(deploymentName)
                .deploy();
        return true;
    }

    @Override
    public String startInstance(String definitionKey, String instanceName,
                                String businessKey, Map<String, Object> instanceValue) {//开启流程实例
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey(definitionKey)
                .withVariables(instanceValue)
                .withName(instanceName)
                .withBusinessKey(businessKey)
                .build()
        );
        return processInstance.getId();
    }

    //    @Override
//    public List<HashMap> searchMyTasks(int startIndex, int pageSize, Map map) {//查询我的待办任务
//        String userName = userService.getLoginUserByAuthentication().getUser().getUsername();//获取访问用户的userName
//        TaskQuery taskQuery = taskService.createTaskQuery().orderByTaskCreateTime().desc()
//                .includeProcessVariables().includeTaskLocalVariables().taskAssignee(userName);//可以改为查询多个执行人的任务
//        //查询条件
//        String status = MapUtil.getStr(map,"status");
//        String creatorName = MapUtil.getStr(map, "creatorName");
//        String type = MapUtil.getStr(map, "type");
//        String instanceId = MapUtil.getStr(map, "instanceId");
//
//        return null;
//    }
    @Override
    public PageUtils searchMyTasks(int startIndex, int pageSize, HashMap map) {//查询我的待办任务
        //查询条件
//        String status = MapUtil.getStr(map, "status");
//        String creatorName = MapUtil.getStr(map, "creatorName");
//        String type = MapUtil.getStr(map, "type");
//        String instanceId = MapUtil.getStr(map, "instanceId");
        map.put("startIndex", startIndex);
        map.put("pageSize", pageSize);

        HashMap resultMap = approvalMapper.searchMyTasks(map);
        List list = JSONUtil.parseArray(resultMap.get("pageList"));
        Long totalCount = (long) resultMap.get("totalCount");
        return new PageUtils(list, totalCount, (Integer) map.get("page"), (Integer) map.get("length"));
    }

    @Override
    public PageUtils searchTasks(String userName, String creatorName, String type, String status, String instanceId, int page, int index) {
        if (!StringUtils.hasText(userName))
            throw new WorkflowException("用户名错误");
        TaskQuery taskQuery = taskService.createTaskQuery().orderByTaskCreateTime().desc()
                .includeTaskLocalVariables().includeProcessVariables().taskAssignee(userName);

        return null;
    }

    @Override
    public boolean completeTask(String taskId, Map<String, Object> variables) {////完成用户的待办任务
        Task task = taskRuntime.task(taskId);
        if (task.getAssignee() == null) {//若为候选人，先拾取任务
            taskRuntime.claim(TaskPayloadBuilder.claim()
                    .withTaskId(task.getId())
                    .build());
        }
        taskRuntime.complete(TaskPayloadBuilder
                .complete()
                .withTaskId(task.getId())
                .withVariables(variables)
                .build());
        log.info("任务执行完成");
        return true;
    }

    @Override
    public Page<ProcessInstance> searchAllInstances(int startIndex, int pageSize) {//查询所有流程实例，即已经开启的流程
        Page<ProcessInstance> processInstancePage = processRuntime
                .processInstances(Pageable.of(startIndex, pageSize));

        return processInstancePage;
    }

    @Override
    public boolean deleteInstance(String instanceId, String reason) {
//        ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder
//                .delete()
//                .withProcessInstanceId(instanceId)
//                .withReason(reason)
//                .build()
//        );
        runtimeService.deleteProcessInstance(instanceId, reason);//删除原因
        log.info("已经删除流程实例");
        return true;
    }


}
