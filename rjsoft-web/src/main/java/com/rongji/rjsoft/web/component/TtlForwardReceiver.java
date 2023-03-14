package com.rongji.rjsoft.web.component;

import com.rabbitmq.client.Channel;
import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
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
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "johnyehyo.test.ttl.queue",
                durable = "true",
                autoDelete = "false",
                arguments = {
                        @Argument(name = "x-dead-letter-exchange", value = "johnyehyo.test.exchange"),
                        @Argument(name = "x-dead-letter-routing-key", value = "johnyehyo.test.key"),
                        @Argument(name = "x-message-ttl",value = "30000", type = "java.lang.Long")
                }),
        exchange = @Exchange(value = "johnyehyo.test.ttl.exchange", durable = "true",
                type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
        key = "johnyehyo.test.ttl.key"
), concurrency = "10")
public class TtlForwardReceiver {

    /**
     * 首次创建完交换机与队列绑定关系后注释掉下面这行
     * 不然如果nack消息会立马投递到死信队列()(消息头显示"x-death=[{reason=expired", 正常应该是"x-death=[{reason=expired")
     * 如果不nack则消息不会被消费(手动ack模式)
     * 正常情况消息将在消息体设置的延时时间后(message.getMessageProperties().setExpiration(delayTimes))被投递到死信队列
     * 或者当消息延时大于队列的x-message-ttl时也将被投递到死信队列
     */
//    @RabbitHandler
    public void handle(@Payload String body, @Headers Map<String, Object> headers, Channel channel) throws IOException {
    }
}
