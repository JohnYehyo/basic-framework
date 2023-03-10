package com.rongji.rjsoft.web.component;

import com.rabbitmq.client.Channel;
import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @description: 延迟队列绑定关系
 * @author: JohnYehyo
 * @create: 2022-01-06 23:42:07
 */
@Component
public class TtlForwardReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "johnyehyo.test.ttl.queue",
                    durable = "true",
                    autoDelete="false",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = "johnyehyo.test.exchange"),
                            @Argument(name = "x-dead-letter-routing-key", value = "johnyehyo.test.key"),
            }),
            exchange = @Exchange(value = "johnyehyo.test.ttl.exchange", durable = "true",
                    type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "johnyehyo.test.ttl.key"
    ), concurrency = "10")
    public void handle(@Payload String body, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        LogUtils.info("接收延迟队列消息:{}, 时间:{}", body, LocalDateTime.now());
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicNack(tag, false, false);
    }
}
