package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 节假日表
 *
 * @TableName tb_holidays
 */
@Data
public class TbHolidays implements Serializable {
    /**
     * 主键
     */
    private Object id;

    /**
     * 日期
     */
    private Date date;

    private static final long serialVersionUID = 1L;
}