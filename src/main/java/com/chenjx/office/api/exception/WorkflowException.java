package com.chenjx.office.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkflowException extends RuntimeException {
    private String msg;
    private int code = 500;

    public WorkflowException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WorkflowException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public WorkflowException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public WorkflowException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}