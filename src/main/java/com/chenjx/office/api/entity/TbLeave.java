package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName tb_leave
 */
@Data
public class TbLeave implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Object userId;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 开始时间
     */
    private Date start;

    /**
     * 结束时间
     */
    private Date end;

    /**
     * 天数
     */
    private String days;

    /**
     * 类型：1病假，2事假
     */
    private Integer type;

    /**
     * 状态：1请假中，2不同意，3已同意
     */
    private Integer status;

    /**
     * 工作流实例ID
     */
    private String instanceId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}