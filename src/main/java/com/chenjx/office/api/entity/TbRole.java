package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 *
 * @TableName tb_role
 */
@Data
public class TbRole implements Serializable {
    /**
     * 主键
     */
    private Object id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限集合
     */
    private Object permissions;

    /**
     * 描述
     */
    private String desc;

    /**
     * 系统角色内置权限
     */
    private Object defaultPermissions;

    /**
     * 是否为系统内置角色
     */
    private Integer systemic;

    private static final long serialVersionUID = 1L;
}