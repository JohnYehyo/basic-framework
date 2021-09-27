package com.rongji.rjsoft.vo.system.branch;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 行政区划树
 * @author: JohnYehyo
 * @create: 2021-09-08 17:40:30
 */
@Data
public class BranchTreeVo implements Serializable {

    private static final long serialVersionUID = 4367617401989033722L;

    /**
     * 行政区划id
     */
    private String branchCode;

    /**
     * 父级行政区划ID
     */
    private String parentCode;

    /**
     * 行政区划名
     */
    private String branchName;

    /**
     * 是否叶子节点
     */
    private boolean parentNode;

}
