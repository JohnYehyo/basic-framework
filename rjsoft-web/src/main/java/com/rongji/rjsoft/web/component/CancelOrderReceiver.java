package com.rongji.rjsoft.web.component;

import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消息消费
 * @author: JohnYehyo
 * @create: 2022-01-06 23:42:07
 */
@Component
@RabbitListener(queues = "johnyehyo.order.cancel")
public class CancelOrderReceiver {

    @RabbitHandler
    public void handle(Object message) {
        LogUtils.info("接收消息:{}", message);
    }
}
