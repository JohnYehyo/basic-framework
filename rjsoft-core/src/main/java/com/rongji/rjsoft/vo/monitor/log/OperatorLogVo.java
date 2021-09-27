package com.rongji.rjsoft.vo.monitor.log;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 操作日志视图
 * @author: JohnYehyo
 * @create: 2021-09-08 14:46:23
 */
@Data
public class OperatorLogVo implements Serializable {

    private static final long serialVersionUID = -6127047116272143469L;

    /**
     * 日志主键
     */
    private Long id;

    /**
     * 模块标题
     */
    private String module;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private Integer operatorType;

    /**
     * 操作人员
     */
    private String userName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 主机地址
     */
    private String ip;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 操作时间
     */
    private LocalDateTime time;


}
