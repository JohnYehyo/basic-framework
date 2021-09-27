package com.rongji.rjsoft.query.monitor.log;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

/**
 * @description: 操作日志查询
 * @author: JohnYehyo
 * @create: 2021-09-08 14:38:14
 */
@Data
public class OperationLogQuery extends PageQuery {

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 行政区划code
     */
    @ApiModelProperty(value = "行政区划code")
    private String branchCode;

    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    private String keyWord;
}
