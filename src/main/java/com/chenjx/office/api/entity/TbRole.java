package com.chenjx.office.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色表
 * @TableName tb_role
 */
@TableName(value ="tb_role")
@Data
public class TbRole implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}