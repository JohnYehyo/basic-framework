package com.rongji.rjsoft.ao.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 栏目表单
 * @author: JohnYehyo
 * @create: 2021-09-15 18:07:11
 */
@Data
public class CmsColumnAo {

    public interface insert {

    }

    public interface update {

    }

    /**
     * 栏目ID
     */
    @ApiModelProperty(value = "栏目ID")
    @NotNull(
            groups = {CmsColumnAo.update.class},
            message = "栏目ID不能为空"
    )
    private Long columnId;

    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称", required = true)
    @NotEmpty(message = "栏目名称不能为空")
    private String columnName;

    /**
     * 栏目父级id
     */
    @ApiModelProperty(value = "父级ID", required = true)
    @NotNull(message = "父级ID不能为空")
    private Long parentId;

}
