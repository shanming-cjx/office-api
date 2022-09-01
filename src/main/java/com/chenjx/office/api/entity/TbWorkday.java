package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName tb_workday
 */
@Data
public class TbWorkday implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 日期
     */
    private Date date;

    private static final long serialVersionUID = 1L;
}