package com.chenjx.office.api.config;

import cn.hutool.json.JSONObject;
import com.chenjx.office.api.exception.OfficeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) throws Exception {
        JSONObject json = new JSONObject();
        //处理后端验证失败产生的异常
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            json.set("error", exception.getBindingResult().getFieldError().getDefaultMessage());
        }
        //处理业务异常
        else if (e instanceof OfficeException) {
            log.error("业务异常", e);
            OfficeException exception = (OfficeException) e;
            json.set("error", exception.getMsg());
        }
        //处理Security的权限异常
        else if(e instanceof AccessDeniedException){
//            log.error("访问的权限不足", e);
//            msg = "访问权限不足";
//            msg = e.getMessage();
//            code = 403;
            throw e;
        }
        //处理Security的登录异常
        else if(e instanceof AuthenticationException){
//            log.error("用户登录认证失败", e);
//            msg = e.getMessage();
//            code = 401;
            throw e;
        }
        //处理其余的异常
        else {
            log.error("执行异常", e);
            json.set("error", "执行异常");
        }

        return json.toString();
    }

//    @ResponseBody
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(NotLoginException.class)
//    public String unLoginHandler(Exception e) {
//        JSONObject json = new JSONObject();
//        json.set("error", e.getMessage());
//        return json.toString();
//    }

}