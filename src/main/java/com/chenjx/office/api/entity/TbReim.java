package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 报销单表
 *
 * @TableName tb_reim
 */
@Data
public class TbReim implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 报销内容
     */
    private Object content;

    /**
     * 总金额
     */
    private BigDecimal amount;

    /**
     * 借款
     */
    private BigDecimal anleihen;

    /**
     * 差额
     */
    private BigDecimal balance;

    /**
     * 类型：1普通报销，2差旅报销
     */
    private Integer typeId;

    /**
     * 状态：1审批中，2已拒绝，3审批通过，4.已归档
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