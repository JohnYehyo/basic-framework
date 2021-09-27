package com.rongji.rjsoft.query.system.dict;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @description: 字典查询
 * @author: JohnYehyo
 * @create: 2021-09-03 17:25:56
 */
@Data
public class DictQuery extends PageQuery {

    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典名称")
    private String dictLabel;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private int status;
}
