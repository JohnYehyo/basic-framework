package com.rongji.rjsoft.web.component;

import com.rabbitmq.client.Channel;
import com.rongji.rjsoft.common.util.LogUtils;
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
public class RabbitMqReceiver2 {


//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "test-key", durable = "true"),
//            exchange = @Exchange(value = "test-ex", durable = "true", type = ExchangeTypes.DIRECT),
//            key = "test-key"
//    ))
    public void onMessage(@Payload String body, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        int prefetchCount = 2;
//        channel.basicQos(prefetchCount);
        try {
            LogUtils.info("消费消息2:" + body);
        } catch (Exception e) {
            LogUtils.error("业务逻辑处理错误");
        } finally {
            channel.basicAck(tag, false);
//            channel.basicNack(tag, false, true);
//            channel.basicNack(tag, false, false);
        }
    }
}
