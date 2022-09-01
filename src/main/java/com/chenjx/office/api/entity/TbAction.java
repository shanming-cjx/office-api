package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 行为表
 *
 * @TableName tb_action
 */
@Data
public class TbAction implements Serializable {
    /**
     * 主键
     */

    private Object id;

    /**
     * 行为编号
     */
    private String actionCode;

    /**
     * 行为名称
     */
    private String actionName;


    private static final long serialVersionUID = 1L;
}