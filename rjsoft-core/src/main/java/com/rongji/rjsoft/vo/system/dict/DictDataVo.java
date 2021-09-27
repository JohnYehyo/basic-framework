package com.rongji.rjsoft.vo.system.dict;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 字典列表视图
 * @author: JohnYehyo
 * @create: 2021-09-06 10:25:07
 */
@Data
public class DictDataVo implements Serializable {

    private static final long serialVersionUID = -797919205202693462L;

    /**
     * 字典编码
     */
    private Long dictCode;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典值
     */
    private String dictValue;
}
