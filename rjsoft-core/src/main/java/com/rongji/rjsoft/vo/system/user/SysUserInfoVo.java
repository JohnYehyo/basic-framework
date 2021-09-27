package com.rongji.rjsoft.vo.system.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 用户详细信息
 * @author: JohnYehyo
 * @create: 2021-08-23 11:29:32
 */
@Data
public class SysUserInfoVo extends SysUserVo implements Serializable {

    private static final long serialVersionUID = -5756209121232312719L;

    /**
     * 角色对象
     */
    private List<Integer> roleIds;

    /**
     * 岗位对象
     */
    private List<Integer> postIds;

}
