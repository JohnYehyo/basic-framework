package com.rongji.rjsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 文章状态
 * @author: JohnYehyo
 * @create: 2021-09-22 15:49:48
 */
@Getter
@AllArgsConstructor
public enum CmsArticleStateEnum {

    DRAFT(0, "草稿"),
    TO_AUDIT(1, "待审核"),
    ENABLE(2, "已发布"),
    NO_PASS(3, "审核不通过"),
    DISABLE(4, "警用");

    private int state;

    private String notes;

}
