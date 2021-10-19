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
public class SearchPageQuery<T> extends PageQuery {

    /**
     * 索引
     */
    @ApiModelProperty(value = "索引", required = true)
//    @NotBlank(message = "索引不能为空")
    private String indexName;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
//    @NotBlank(message = "类型不能为空")
    private String indexType;

    /**
     * 搜索对象
     */
    @ApiModelProperty(value = "搜索键")
    private List<SearchBaseQuery> param;

    /**
     * 搜索值
     */
    @ApiModelProperty(value = "搜索值")
    private List<String> values;

    /**
     * 泛型
     */
    @ApiModelProperty(value = "泛型")
    private Class<T> clazz;

    /**
     * 时间键
     */
    @ApiModelProperty(value = "时间键")
    private String timeKey;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Long sTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Long eTime;

    /**
     * 行政区划键
     */
    @ApiModelProperty(value = "行政区划键")
    private String branchKey;

    /**
     * 行政区划值
     */
    @ApiModelProperty(value = "行政区划值")
    private String branchCode;

}
