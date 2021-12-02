package com.rongji.rjsoft.query.search;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @description: 搜索分页查询
 * @author: JohnYehyo
 * @create: 2021-10-12 15:33:41
 */
@Data
@ApiModel(value = "搜索分页查询")
public class SearchMultiPageQuery<T> extends PageQuery {

    /**
     * 索引
     */
    @ApiModelProperty(value = "索引", required = true)
    @NotBlank(message = "索引不能为空")
    private String indexName;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String indexType;

    /**
     * 搜索条件
     */
    @ApiModelProperty(value ="搜索条件")
    private SearchMultiBaseQuery param;


    /**
     * 泛型
     */
    @ApiModelProperty(value = "泛型")
    private Class<T> clazz;

}
