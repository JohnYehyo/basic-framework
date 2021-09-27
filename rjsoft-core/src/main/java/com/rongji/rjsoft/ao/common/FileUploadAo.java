package com.rongji.rjsoft.ao.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 文件上传
 * @author: JohnYehyo
 * @create: 2021-09-24 13:04:35
 */
@Data
public class FileUploadAo {

    /**
     *业务类型
     */
    @ApiModelProperty(value = "业务类型", required = true)
    @NotBlank(message = "业务类型不能为空")
    private String businessType;


    /**
     *oss存储对象id
     */
    @ApiModelProperty(value = "oss存储对象id")
    private String fileName;
}
