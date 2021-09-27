package com.rongji.rjsoft.vo.system.post;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 岗位列表视图
 * @author: JohnYehyo
 * @create: 2021-09-02 10:37:00
 */
@Data
public class SysPostVo implements Serializable {
    private static final long serialVersionUID = 8748571557521061039L;

    /**
     * 岗位ID
     */
    private Long postId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 显示顺序
     */
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
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
