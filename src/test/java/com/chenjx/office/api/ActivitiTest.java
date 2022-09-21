package com.chenjx.office.api;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ActivitiTest {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private ProcessRuntime processRuntime;
    @Resource
    private TaskService taskService;

    //通过bpmn部署流程
    @Test
    public void initDeploymentBPMN(){
        String filename="BPMN/meeting.bpmn20.xml";
        Deployment deployment=repositoryService.createDeployment()
                .addClasspathResource(filename)
                .name("线下会议申请")
                .deploy();
        System.out.println(deployment.getName());
    }

    @Test
    public void delProcessInstance() {
        runtimeService.deleteProcessInstance("864edc11-26e3-11ed-a170-645d86a74fae", "测试删除");//删除原因
        System.out.println("删除流程实例");
    }

    @Test
    public void getTasksByAssignee(){
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("wangwu")
                .list();
        for(Task tk : list){
            System.out.println("Id："+tk.getId());
            System.out.println("Name："+tk.getName());
            System.out.println("Assignee："+tk.getAssignee());
        }

    }

}
