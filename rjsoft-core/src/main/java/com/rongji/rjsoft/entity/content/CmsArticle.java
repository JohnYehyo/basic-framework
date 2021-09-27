package com.rongji.rjsoft.entity.content;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章信息表
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CmsArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 文章封面图片
     */
    private String coverImage;

    /**
     * 文章专属二维码地址
     */
    private String qrcodePath;

    /**
     * 附件地址
     */
    private String fileUrl;

    /**
     * 是否置顶 0 否 1 是
     */
    private int top;

    /**
     * 类型
     */
    private Long categoryId;

    /**
     * 是否轮播 0 否 1 是
     */
    private int slider;

    /**
     * 轮播图地址
     */
    private String sliderImg;

    /**
     * 是否原创 0: 是 1:否
     */
    private int original;

    /**
     * 文章简介，最多200字
     */
    private String description;

    /**
     * 文章关键字，优化搜索
     */
    private String keywords;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 状态 0 草稿 1 待发布  2 待审核 3 已发布 4 禁用
     */
    private int state;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
