package com.rongji.rjsoft.vo.common;

import lombok.Data;

/**
 * @description: 附件视图
 * @author: JohnYehyo
 * @create: 2021-10-20 16:42:26
 */
@Data
public class SysCommonFileVo {

    /**
     * 附件名
     */
    private String fileName;

    /**
     * 附件地址
     */
    private String fileUrl;

    /**
     * 附件类型
     */
    private int fileType;
}
