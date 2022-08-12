package com.chenjx.office.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 罚金表
 * @TableName tb_amect
 */
@TableName(value ="tb_amect")
@Data
public class TbAmect implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * UUID
     */
    private String uuid;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 罚款金额
     */
    private BigDecimal amount;

    /**
     * 罚款类型
     */
    private Integer typeId;

    /**
     * 罚款原因
     */
    private String reason;

    /**
     * 微信支付单ID
     */
    private String prepayId;

    /**
     * 状态：1未缴纳，2已缴纳
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}