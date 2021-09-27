package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 岗位传递参数
 * @author: JohnYehyo
 * @create: 2021-09-02 09:47:01
 */
@Data
public class SysPostAo {

    public interface insert {

    }

    public interface update {

    }

    /**
     * 岗位id
     */
    @ApiModelProperty(value = "岗位id", dataType = "Long")
    @NotNull(
            groups = {SysPostAo.update.class},
            message = "岗位id不能为空"
    )
    private Long postId;

    private String postCode = null;

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称", required = true, dataType = "String")
    @NotEmpty(groups = {SysPostAo.insert.class, SysPostAo.update.class}, message = "岗位名称不能空")
    private String postName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序不能空", required = true, dataType = "int")
    @NotNull(groups = {SysPostAo.insert.class, SysPostAo.update.class}, message = "显示顺序不能为空")
    private Integer postSort;


    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "岗位状态", dataType = "int")
    private int status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;
}
