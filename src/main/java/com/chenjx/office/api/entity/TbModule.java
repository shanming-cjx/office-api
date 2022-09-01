package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 模块资源表
 *
 * @TableName tb_module
 */
@Data
public class TbModule implements Serializable {
    /**
     * 主键
     */
    private Object id;

    /**
     * 模块编号
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;

    private static final long serialVersionUID = 1L;
}