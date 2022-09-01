package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName tb_dept
 */
@Data
public class TbDept implements Serializable {
    /**
     * 主键
     */
    private Object id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门电话
     */
    private String tel;

    /**
     * 部门邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String desc;

    private static final long serialVersionUID = 1L;
}