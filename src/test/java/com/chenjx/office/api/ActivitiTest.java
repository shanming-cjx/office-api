package com.chenjx.office.api;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ActivitiTest {
    @Resource
    private RepositoryService repositoryService;

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
}
