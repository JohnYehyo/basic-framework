package com.rongji.rjsoft.common.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 * @author JohnYehyo
 * @date 2020-10-14
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateAnnotation {

    /**
     * 时间范围(秒)
     * @return
     */
    int time() default 60;

    /**
     * 最大次数
     * @return
     */
    int maxCount() default 10;
}
