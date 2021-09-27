package com.rongji.rjsoft.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 通用分页
 * @author: JohnYehyo
 * @create: 2021-08-17 11:14:50
 */
@Data
public class CommonPage<T> {

    private Long current;
    private Long pageSize;
    private Long totalPage;
    private Long total;
    private List<T> list;
}
