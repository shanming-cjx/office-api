package com.chenjx.office.api.controller;

import com.chenjx.office.api.common.util.Resp;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@Tag(name = "DocTestController",description = "用户Web接口测试")
@SuppressWarnings({"all"})
public class DocTestController {//访问localhost:8090/office-api/swagger-ui.html【即springboot地址端口/项目名/swagger-ui.html（yml配置）】
    @PostMapping("/checkCode")
    @Operation(summary = "检测登陆验证码")
    public Resp checkCode(@Valid String form) {
        return Resp.ok().put("result", true);
    }

    @GetMapping("/build")
    @Operation(summary = "热部署测试")
    public String hotDeployTest(){
        return "hotDeployTest";
    }
}
