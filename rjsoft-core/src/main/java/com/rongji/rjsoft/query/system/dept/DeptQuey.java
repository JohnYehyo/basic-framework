package com.rongji.rjsoft.query.system.dept;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @description: 部门查询
 * @author: JohnYehyo
 * @create: 2021-08-30 14:17:50
 */
@Data
public class DeptQuey extends PageQuery {

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", dataType = "String")
    private String deptName;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门类型", dataType = "int")
    private int deptType;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "部门状态", dataType = "int")
    private Integer status;
}
