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
 * 站点信息表
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CmsSite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 站点ID
     */
    @TableId(value = "site_id", type = IdType.AUTO)
    private Long siteId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点描述
     */
    private String description;

    /**
     * 父级站点id
     */
    private Long parentId;

    /**
     * 树关系
     */
    private String ancestors;

    /**
     * 站点域名
     */
    private String siteUrl;

    /**
     * 站点目录
     */
    private String siteFile;

    /**
     * 排序
     */
    private Integer orderNum;

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
