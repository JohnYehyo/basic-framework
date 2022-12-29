package com.rongji.rjsoft.enums;

/**
 * @Description RabbitMQ消费状态
 * @Author JohnYehyo
 * @Date 2020-06-18 09:46
 * @Version 1.0
 */
public enum RabbitResult {

    /**
     * 成功
     */
    SUCCESS,
    /**
     * 重试
     */
    RETRY,
    /**
     * 丢弃
     */
    DISCARDED
}
