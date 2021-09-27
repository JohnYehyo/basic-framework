package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 字典数据表单数据
 * @author: JohnYehyo
 * @create: 2021-09-03 17:02:12
 */
@Data
public class SysDictDataAo {

    public interface insert {

    }

    public interface update {

    }

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码")
    @NotNull(
            groups = {SysDictDataAo.update.class},
            message = "字典编码不能为空"
    )
    private Long dictCode;

    /**
     * 字典排序
     */
    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典标签", required = true)
    @NotEmpty(groups = {SysDictDataAo.insert.class, SysDictDataAo.update.class}, message = "字典标签不能为空")
    private String dictLabel;

    /**
     * 字典键值
     */
    @ApiModelProperty(value = "字典键值", required = true)
    @NotEmpty(groups = {SysDictDataAo.insert.class, SysDictDataAo.update.class}, message = "字典键值不能为空")
    private String dictValue;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型", required = true)
    @NotEmpty(groups = {SysDictDataAo.insert.class, SysDictDataAo.update.class}, message = "字典类型不能为空")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态")
    private int status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
