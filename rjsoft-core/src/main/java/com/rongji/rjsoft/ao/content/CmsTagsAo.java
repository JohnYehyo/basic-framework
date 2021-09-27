package com.rongji.rjsoft.ao.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 标签表单
 * @author: JohnYehyo
 * @create: 2021-09-18 09:15:18
 */
@Data
public class CmsTagsAo {

    public interface insert{

    }

    public interface update{

    }

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    @NotNull(
            groups = {CmsTagsAo.update.class},
            message = "标签id不能为空"
    )
    private Long tagId;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名", required = true)
    @NotEmpty(message = "标签名不能为空")
    private String tagName;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
}
