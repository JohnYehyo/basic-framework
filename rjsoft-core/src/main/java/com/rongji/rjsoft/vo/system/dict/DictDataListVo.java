package com.rongji.rjsoft.vo.system.dict;

import lombok.Data;

import java.util.List;

/**
 * @description: 字典列表
 * @author: JohnYehyo
 * @create: 2021-09-08 10:49:54
 */
@Data
public class DictDataListVo {

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典列表
     */
    private List<DictDataVo> list;
}
