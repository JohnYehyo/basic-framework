package com.rongji.rjsoft.web.component;

import com.rabbitmq.client.Channel;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.RabbitResult;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
public class RabbitMqReceiver4 {


    /**
     * 交换器类型设置为topic, 绑定各自不同的队列,
     * 路由键使用"#"(匹配一个或多个词)或"*"(匹配一个词)
     * 可以使用topic的通配符模式
     * @param body
     * @param headers
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "am-topic-queue", durable = "true"),
            exchange = @Exchange(value = "am-topic-ex", durable = "true", type = ExchangeTypes.TOPIC),
            key = "am.#"
    ), concurrency = "10")
    public void onMessage(@Payload String body, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String correlationId = (String) headers.get("spring_returned_message_correlation");
        int prefecthSize = 0;
        int prefetchCount = 30;
        boolean global = false;
        channel.basicQos(prefecthSize, prefetchCount, global);
        RabbitResult rr = RabbitResult.RETRY;
        try {
            LogUtils.info("[统一消息][接收端4]消费消息:{}, 内容:{}", correlationId, body);
            rr = RabbitResult.SUCCESS;
        } catch (Exception e) {
            rr = RabbitResult.DISCARDED;
            LogUtils.error("[统一消息][接收端4]消息:{}, 业务逻辑处理错误", correlationId, e);
        } finally {
            if (rr == RabbitResult.SUCCESS) {
                //告诉服务器收到这条消息 无需再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
                channel.basicAck(tag, false);
            } else if (rr == RabbitResult.RETRY) {
                channel.basicNack(tag, false, true);
            } else {
                channel.basicNack(tag, false, false);
            }
        }
    }
}
