package com.rongji.rjsoft.query.search;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description: 搜索多键值对象
 * @author: JohnYehyo
 * @create: 2021-10-12 17:05:32
 */
@Data
@ApiModel(value = "搜索键值对象")
public class SearchMultiBaseQuery {

    private String[] key;

    private String value;
}
