package com.rongji.rjsoft.query.system.post;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: 岗位查询
 * @author: JohnYehyo
 * @create: 2021-08-30 14:17:50
 */
@Data
public class PostQuery extends PageQuery {

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称", dataType = "String")
    private String postName;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "岗位状态", dataType = "int")
    private int status = 0;
}
