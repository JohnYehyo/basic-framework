package com.rongji.rjsoft.entity.content;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 栏目信息表
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CmsColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 栏目ID
     */
    @TableId(type = IdType.AUTO)
    private Long columnId;

    /**
     * 栏目名称
     */
    private String columnName;

    /**
     * 父级栏目id
     */
    private Long parentId;

    /**
     * 树关系
     */
    private String ancestors;

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
