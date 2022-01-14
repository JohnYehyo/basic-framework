package com.rongji.rjsoft.enums;

import lombok.Getter;

/**
 * @description: 消息队列
 * @author: JohnYehyo
 * @create: 2022-01-06 23:27:13
 */
@Getter
public enum QueueEnum {

    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("johnyehyo.order.direct", "johnyehyo.order.cancel", "johnyehyo.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("johnyehyo.order.direct.ttl", "johnyehyo.order.cancel.ttl", "johnyehyo.order.cancel.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
