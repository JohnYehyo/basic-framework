package com.rongji.rjsoft.web.component;

import com.rabbitmq.client.Channel;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.RabbitResult;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @description: 消息消费
 * @author: JohnYehyo
 * @create: 2022-01-06 23:42:07
 */
@Component
@RabbitListener(queues = "johnyehyo.order.cancel")
public class CancelOrderReceiver {

    @RabbitHandler
    public void handle(Object message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 单条消息的大小限制，一般设为0或不设置，不限制大小
        int prefecthSize = 0;
        // 不要同时给消费端推送n条消息，一旦有n个消息还没ack，则该consumer将block掉，直到有ack 注意在自动应答下不生效
        int prefetchCount = 30;
        // 表示是否应用于channel上，即是channel级别还是consumer级别
        boolean global = false;
        channel.basicQos(prefecthSize, prefetchCount, global);

        RabbitResult rr = RabbitResult.RETRY;
        try {
            //具体业务处理...
            LogUtils.info("[统一消息][接收端]消费消息, 内容:{}, 时间:{}", message, LocalDateTime.now());
            rr = RabbitResult.SUCCESS;
        } catch (Exception e) {
            rr = RabbitResult.DISCARDED;
            LogUtils.error("[统一消息][接收端]消息, 业务逻辑处理错误", e);
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
