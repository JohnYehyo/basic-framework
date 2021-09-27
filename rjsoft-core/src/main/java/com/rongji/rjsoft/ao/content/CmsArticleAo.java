package com.rongji.rjsoft.ao.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 文章表单
 * @author: JohnYehyo
 * @create: 2021-09-22 14:50:43
 */
@Data
public class CmsArticleAo {

    public interface insert{

    }

    public interface update{

    }

    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID")
    @NotNull(
            groups = CmsArticleAo.update.class,
            message = "文章ID不能为空"
    )
    private Long articleId;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题", required = true)
    @NotEmpty(message = "文章标题不能为空")
    private String articleTitle;

    /**
     * 作者ID
     */
    @ApiModelProperty(value = "作者ID", notes = "无需传, 系统自动记录")
    private Long authorId;

    /**
     * 作者姓名
     */
    @ApiModelProperty(value = "作者姓名", notes = "无需传, 系统自动记录")
    private String authorName;

    /**
     * 文章封面图片
     */
    @ApiModelProperty(value = "文章封面图片")
    private String coverImage;

    /**
     * 文章专属二维码地址
     */
    @ApiModelProperty(value = "文章专属二维码地址")
    private String qrcodePath;

    /**
     * 附件地址
     */
    @ApiModelProperty(value = "附件地址")
    private String fileUrl;

    /**
     * 是否置顶 0 否 1 是
     */
    @ApiModelProperty(value = "是否置顶", notes = "0 否 1 是")
    private int top;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    private Long categoryId;

    /**
     * 是否轮播 0 否 1 是
     */
    @ApiModelProperty(value = "是否轮播", notes = "0 否 1 是")
    private int slider;

    /**
     * 轮播图地址
     */
    @ApiModelProperty(value = "轮播图地址")
    private String sliderImg;

    /**
     * 是否原创 0: 是 1:否
     */
    @ApiModelProperty(value = "是否原创", notes = "0:是 1:否")
    private int original;

    /**
     * 文章简介，最多200字
     */
    @ApiModelProperty(value = "文章简介", notes = "最多200字")
    private String description;

    /**
     * 文章关键字，优化搜索
     */
    @ApiModelProperty(value = "文章搜索关键字", required = true)
    private String keywords;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    /**
     * 状态 0 草稿 1 待审核 2 审核不通过 3 已发布 4 禁用
     */
    @ApiModelProperty(value = "文章状态", notes = "0 草稿 1 待审核 2 审核不通过 3 已发布 4 禁用")
    private int state;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", required = true)
    @NotNull(message = "标签不能为空")
    private Long[] tagIds;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容", required = true)
    @NotEmpty(message = "文章内容不能为空")
    private String content;

}
