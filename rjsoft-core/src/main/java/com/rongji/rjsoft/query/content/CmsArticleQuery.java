package com.rongji.rjsoft.query.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: 文章搜索对象
 * @author: JohnYehyo
 * @create: 2021-09-22 15:01:12
 */
@Data
public class CmsArticleQuery {

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    @NotEmpty(message = "文章标题不能为空")
    private String articleTitle;

    /**
     * 文章关键字，优化搜索
     */
    @ApiModelProperty(value = "文章搜索关键字")
    private String keywords;

    /**
     * 状态0 草稿 1 待审核 2 审核不通过 3 已发布 4 禁用
     */
    @ApiModelProperty(value = "文章状态", notes = "2 审核不通过 3 已发布 4 禁用")
    private int state;
}
