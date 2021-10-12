package com.rongji.rjsoft.query.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @description: 搜索查询
 * @author: JohnYehyo
 * @create: 2021-10-12 15:33:41
 */
@Data
@ApiModel(value = "搜索查询")
public class SearchQuery<T> {

    /**
     * 索引
     */
    @ApiModelProperty(value = "索引", required = true)
    @NotBlank(message = "索引不能为空")
    private String indexName;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空")
    private String indexType;

    /**
     * 搜索对象
     */
    @ApiModelProperty(value = "搜索键")
    private List<SearchBaseQuery> param;

    /**
     * 泛型
     */
    @ApiModelProperty(value = "泛型")
    private Class<T> clazz;
}
