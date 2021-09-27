package com.rongji.rjsoft.vo.content;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 文章列表视图
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
@Data
public class CmsArticleVo implements Serializable {


    private static final long serialVersionUID = 9175886446556841665L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 文章封面图片
     */
    private String coverImage;

    /**
     * 类型
     */
    private String categoryName;

    /**
     * 是否原创 0: 是 1:否
     */
    private int original;

    /**
     * 状态 0 草稿 1 待发布  2 待审核 3 已发布 4 禁用
     */
    private int state;

    /**
     * 是否置顶
     */
    private int top;

    /**
     * 是否轮播
     */
    private int slider;

    /**
     * 文章标签
     */
    private List<String> tags;

}
