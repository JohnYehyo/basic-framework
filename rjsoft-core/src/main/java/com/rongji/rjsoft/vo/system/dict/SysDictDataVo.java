package com.rongji.rjsoft.vo.system.dict;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 字典数据视图
 * @author: JohnYehyo
 * @create: 2021-09-03 17:01:29
 */
@Data
public class SysDictDataVo implements Serializable {

    private static final long serialVersionUID = 2542782125767945118L;

    /**
     * 字典code
     */
    private String dictCode;

    /**
     * 字典排序
     */
    private int dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典类别
     */
    private String dictType;

    /**
     * 字典状态
     */
    private int status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

}
