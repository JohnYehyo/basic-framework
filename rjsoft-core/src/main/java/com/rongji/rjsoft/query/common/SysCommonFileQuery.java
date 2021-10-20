package com.rongji.rjsoft.query.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 附件查询
 * @author: JohnYehyo
 * @create: 2021-10-20 16:44:16
 */
@Data
@ApiModel(value = "附件查询")
public class SysCommonFileQuery {

    /**
     * 业务表id
     */
    @ApiModelProperty(value = "业务表id", required = true)
    @NotNull(message = "业务表id不能为空")
    private Long tableId;

    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型", required = true)
    @NotNull(message = "业务类型不能为空")
    private String tableType;

    /**
     * 附件类型
     */
    @ApiModelProperty(value = "附件类型")
    private Integer fileType;
}
