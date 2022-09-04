package com.chenjx.office.api.exception;

import com.alibaba.fastjson.JSON;
import com.chenjx.office.api.common.util.Resp;
import com.chenjx.office.api.common.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String msg = "认证失败请重新登录";
        if (authException instanceof BadCredentialsException)//输出UsernameNotFoundException手动抛出的异常信息
            msg = authException.getMessage();
        Resp result = Resp.error(HttpStatus.UNAUTHORIZED.value(), msg);
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,json);
    }
}
