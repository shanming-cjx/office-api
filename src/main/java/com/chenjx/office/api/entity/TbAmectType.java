package com.chenjx.office.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 罚金类型表
 * @TableName tb_amect_type
 */
@TableName(value ="tb_amect_type")
@Data
public class TbAmectType implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类别
     */
    private String type;

    /**
     * 罚金
     */
    private BigDecimal money;

    /**
     * 是否为系统内置
     */
    private Integer systemic;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}