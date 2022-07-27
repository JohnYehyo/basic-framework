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
import java.util.Map;

/**
 * @description: rabbitmq消费
 * @author: JohnYehyo
 * @create: 2022-01-13 11:10:47
 */
@Component
public class RabbitMqReceiver {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "test-key", durable = "true"),
            exchange = @Exchange(value = "test-ex", durable = "true", type = ExchangeTypes.DIRECT),
            key = "test-key"
    ), concurrency = "10")
    public void onMessage(@Payload String body, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        LogUtils.info(body + ":业务处理开始......");
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String correlationId = (String) headers.get("spring_returned_message_correlation");
        int prefetchCount = 5;
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.info(body + ":业务处理完毕");
//        channel.basicQos(0, 2, false);
        try {
//            LogUtils.info("消费消息1:" + body);
        } catch (Exception e) {
            LogUtils.error("业务逻辑处理错误");
        } finally {
            channel.basicAck(tag, false);
//            channel.basicNack(tag, false, true);
//            channel.basicNack(tag, false, false);
        }
    }
}
