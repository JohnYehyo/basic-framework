package com.rongji.rjsoft.vo.system.post;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 岗位下拉列表视图
 * @author: JohnYehyo
 * @create: 2021-09-02 10:37:00
 */
@Data
public class SysPostSelectVo implements Serializable {


    private static final long serialVersionUID = 5869813615264835164L;

    /**
     * 岗位ID
     */
    private Long postId;

    /**
     * 岗位名称
     */
    private String postName;

}
