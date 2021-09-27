package com.rongji.rjsoft.entity.content;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章标签关系表
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CmsArticleTags implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 标签id
     */
    private Long tagId;


}
