package com.chenjx.office.api.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "删除用户表单")
public class DeleteUserByIdsRequest {
    @NotEmpty(message = "ids不能为空")
    @Schema(description = "用户ID")
    private Integer[] ids;
}

