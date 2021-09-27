package com.rongji.rjsoft.query.content;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 文章类别查询对象
 * @author: JohnYehyo
 * @create: 2021-09-15 18:25:16
 */
@Data
public class CmsCategoryQuery extends PageQuery {

    /**
     * 文章类别ID
     */
    @ApiModelProperty(value = "栏目ID")
    private Long categoryId;

    /**
     * 文章类别名
     */
    @ApiModelProperty(value = "栏目名")
    private String categoryName;

}
