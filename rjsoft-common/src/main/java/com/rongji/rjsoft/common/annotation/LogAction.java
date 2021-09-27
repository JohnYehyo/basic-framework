package com.rongji.rjsoft.common.annotation;

import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * @description: 操作日志
 * @author: JohnYehyo
 * @create: 2021-09-08 09:31:46
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAction {

    /**
     * 模块
     * @return
     */
    String module() default "";

    /**
     * 方法
     * @return
     */
    String method() default "";

    /**
     * 操作类型
     * @return
     */
    LogTypeEnum logType() default LogTypeEnum.OTHER;

    /**
     * 操作人类别
     * @return
     */
    OperatorTypeEnum operatorType() default OperatorTypeEnum.OTHER;

    /**
     * 是否记录参数
     * @return
     */
    boolean isRecord() default true;
}
