package com.rongji.rjsoft.dto.content;

import lombok.Data;

/**
 * @description: 文章状态
 * @author: JohnYehyo
 * @create: 2021-09-22 15:48:25
 */
@Data
public class CmsArticleStateDto {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章状态 0 草稿 1 待审核 2 审核不通过 3 已发布 4 禁用
     */
    private int state;
}
