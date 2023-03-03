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
public class RabbitMqReceiver2 {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "test-key", durable = "true"),
            exchange = @Exchange(value = "test-ex", durable = "true", type = ExchangeTypes.DIRECT),
            key = "test-key"
    ), concurrency = "20")
    public void onMessage(@Payload String body, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String correlationId = (String) headers.get("spring_returned_message_correlation");
        // 单条消息的大小限制，一般设为0或不设置，不限制大小
        int prefecthSize = 0;
        // 不要同时给消费端推送n条消息，一旦有n个消息还没ack，则该consumer将block掉，直到有ack 注意在自动应答下不生效
        int prefetchCount = 1;
        // 表示是否应用于channel上，即是channel级别还是consumer级别
        boolean global = false;
        channel.basicQos(prefecthSize, prefetchCount, global);

//        try {
//            Thread.sleep(2000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        RabbitResult rr = RabbitResult.RETRY;
        try {
            //具体业务处理...
            LogUtils.info("[统一消息][接收端2]消费消息:{}, 内容:{}", correlationId, body);
            rr = RabbitResult.SUCCESS;
        } catch (Exception e) {
            rr = RabbitResult.DISCARDED;
            LogUtils.error("[统一消息][接收端2]消息{}, 业务逻辑处理错误", correlationId, e);
        } finally {
            if (rr == RabbitResult.SUCCESS) {
                channel.basicAck(tag, false);
            } else if (rr == RabbitResult.RETRY) {
                channel.basicNack(tag, false, true);
            } else {
                channel.basicNack(tag, false, false);
            }
        }
    }
}
