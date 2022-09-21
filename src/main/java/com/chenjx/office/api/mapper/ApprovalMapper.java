package com.chenjx.office.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * @author chenjx
 * @description 对审批任务的操作
 * @createDate 2022-09-18 23:12:22
 */
@Mapper
public interface ApprovalMapper {
    HashMap searchMyTasks(HashMap map);

}




