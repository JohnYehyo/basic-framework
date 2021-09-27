package com.rongji.rjsoft.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.vo.CommonPage;

/**
 * @description: 分页工具
 * @author: JohnYehyo
 * @create: 2021-08-17 11:30:42
 */
public class CommonPageUtils {

    public static <T> CommonPage<T> assemblyPage(IPage<T> info) {
        if (null == info) {
            return null;
        }
        CommonPage<T> result = new CommonPage<>();
        result.setTotalPage(info.getPages());
        result.setTotal(info.getTotal());
        result.setCurrent(info.getCurrent());
        result.setPageSize(info.getSize());
        result.setList(info.getRecords());
        return result;
    }
}
