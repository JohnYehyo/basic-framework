package com.rongji.rjsoft.entity.content;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章内容
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CmsArticleContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章内容
     */
    private String content;


}
