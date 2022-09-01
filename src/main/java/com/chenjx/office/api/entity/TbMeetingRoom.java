package com.chenjx.office.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 会议室表
 *
 * @TableName tb_meeting_room
 */
@Data
public class TbMeetingRoom implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 会议室名称
     */
    private String name;

    /**
     * 最大人数
     */
    private Integer max;

    /**
     * 备注
     */
    private String desc;

    /**
     * 状态，0不可用，1可用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}