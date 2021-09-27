package com.rongji.rjsoft.ao.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 文章审核
 * @author: JohnYehyo
 * @create: 2021-09-22 15:11:54
 */
@Data
public class CmsArticleAuditAo {

    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID", required = true)
    @NotNull(message = "文章ID不能为空")
    private List<Long> articleIds;

    /**
     * 状态0 草稿 1 待审核 2 审核不通过 3 已发布 4 禁用
     */
    @ApiModelProperty(value = "文章状态", required = true, notes = "2 审核不通过 3 已发布 4 禁用")
    private int state;
}
