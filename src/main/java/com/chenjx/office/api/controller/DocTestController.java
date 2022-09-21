package com.chenjx.office.api.controller;

import cn.hutool.json.JSONUtil;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.controller.request.SearchTaskByPageRequest;
import com.chenjx.office.api.entity.security.LoginUser;
import com.chenjx.office.api.mapper.ApprovalMapper;
import com.chenjx.office.api.service.TbUserService;
import com.chenjx.office.api.service.WorkFlowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RepositoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;


@RestController
@RequestMapping("/test")
@Tag(name = "DocTestController", description = "用户Web接口测试")
@Slf4j
@SuppressWarnings({"all"})
public class DocTestController {//访问localhost:8090/office-api/swagger-ui.html【即springboot地址端口/项目名/swagger-ui.html（yml配置）】

    @Resource
    private TbUserService userService;
    @Resource
    private WorkFlowService workFlowService;
    @Resource
    private ApprovalMapper approvalMapper;

    @Resource
    private TaskRuntime taskRuntime;
    @Resource
    private ProcessRuntime processRuntime;
    @Resource
    private RepositoryService repositoryService;


    @PostMapping("/checkCode")
    @Operation(summary = "检测登陆验证码")
    public Resp checkCode(@Valid String form) {
        return Resp.ok().put("result", true);
    }

    @GetMapping("/build")
    @Operation(summary = "热部署测试")
    public String hotDeployTest() {
        return "hotDeployTest";
    }

    @PostMapping("/task_query")
    @Operation(summary = "我的流程任务查询")
    public Page<Task> searchTaskByPage(@Valid @RequestBody SearchTaskByPageRequest req) {
        HashMap map = JSONUtil.parse(req).toBean(HashMap.class);
        LoginUser loginUserInfo = userService.getLoginUserByAuthentication();
        map.put("userId", loginUserInfo.getUser().getId());
        map.put("role", loginUserInfo.getRoles());
        int pageIndex = (Integer) map.get("page") - 1;
        int pageSize = (Integer) map.get("length");
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(pageIndex, pageSize));
        for (Task task : tasks.getContent()) {
//            List<VariableInstance> list = processRuntime.variables(ProcessPayloadBuilder
//                    .variables()
//                    .withProcessInstanceId(task.getProcessInstanceId())
//                    .build()
//            );
            System.out.println(task);

        }
        return tasks;
    }

    @GetMapping("/delete")
    @Operation(summary = "删除流程实例")
    public Resp deleteInstance(String processInstance, String reason) {
        boolean result = workFlowService.deleteInstance(processInstance, reason);
        log.info("删除结果：" + result);
        return Resp.ok().put("result", result);
    }

    @GetMapping("/search_all_instances")
    @Operation(summary = "查询所有流程实例")
    public Resp searchAllInstance(int index, int pageSize) {

        return Resp.ok().put("Page", workFlowService.searchAllInstances(index, pageSize));
    }

    @GetMapping("/complete")
    @Operation(summary = "完成我的的流程任务")
    public Resp completeMyTask(String taskId, String result, String status) {
        Task task = taskRuntime.task(taskId);
        if (task.getAssignee() == null) {
            taskRuntime.claim(TaskPayloadBuilder.claim()
                    .withTaskId(task.getId())
                    .build());
        }
        taskRuntime.complete(TaskPayloadBuilder
                .complete()
                .withTaskId(task.getId())
                .withVariable("result", result)
                        .withVariable("status",status)
                .build());
        System.out.println("任务执行完成");
        return Resp.ok();
    }

    @GetMapping("delete_process")
    @Operation(summary = "删除流程定义")
    public void delDefinition(String pdId) {
        repositoryService.deleteDeployment(pdId, true);//false会保留历史
        System.out.println("删除流程定义成功");
    }

//    @GetMapping("search_my_tasks")
//    @Operation(summary = "查询我的任务")
//    public Resp searchMyTasks() {
//        String userName = userService.getLoginUserByAuthentication().getUsername();
//        HashMap map = new HashMap();
//        map.put("userName", userName);
//        map.put("start", 0);
//        map.put("size", 10);
//        HashMap list = approvalMapper.searchMyTasks(map);
////        List<Map> list1 = new ArrayList();
////        for (HashMap map1 :list) {
////            Map map2 = JSONUtil.parseObj(map1.get("variable"));
////            list1.add(map2);
////        }
////        for(HashMap map1 :list){
////            System.out.println(map1);
////        }
//        System.out.println(list.get("pageList"));
//
//
//        return Resp.ok().put("pageDate", JSONUtil.parseArray(list.get("pageList")));
//    }
}
