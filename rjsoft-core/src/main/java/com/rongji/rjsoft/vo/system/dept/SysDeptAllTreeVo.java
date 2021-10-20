package com.rongji.rjsoft.vo.system.dept;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 部门同步树
 * @author: JohnYehyo
 * @create: 2021-09-09 10:47:42
 */
@Data
public class SysDeptAllTreeVo implements Serializable {

    private static final long serialVersionUID = 676873841324848348L;
    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 是否叶子节点
     */
    private List<SysDeptAllTreeVo> children;
}
