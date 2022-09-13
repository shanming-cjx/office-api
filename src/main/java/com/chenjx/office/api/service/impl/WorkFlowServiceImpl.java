package com.chenjx.office.api.service.impl;

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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public Page<Task> searchMyTasks(int startIndex, int pageSize) {//查询我的待办任务
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(startIndex, pageSize));
        return tasks;
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
        ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder
                .delete()
                .withProcessInstanceId(instanceId)
                .withReason(reason)
                .build()
        );
        return true;
    }


}
