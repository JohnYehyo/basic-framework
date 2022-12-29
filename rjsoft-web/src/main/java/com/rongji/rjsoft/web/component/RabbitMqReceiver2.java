//package com.rongji.rjsoft.web.component;
//
//import com.rabbitmq.client.Channel;
//import com.rongji.rjsoft.common.util.LogUtils;
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.messaging.handler.annotation.Headers;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// * @description: rabbitmq消费
// * @author: JohnYehyo
// * @create: 2022-01-13 11:10:47
// */
//@Component
//public class RabbitMqReceiver2 {
//
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "test-key", durable = "true"),
//            exchange = @Exchange(value = "test-ex", durable = "true", type = ExchangeTypes.DIRECT),
//            key = "test-key"
//    ), concurrency = "5")
//    public void onMessage(@Payload String body, @Headers Map<String, Object> headers, Channel channel) throws IOException {
//        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
//        String correlationId = (String) headers.get("spring_returned_message_correlation");
//        // 单条消息的大小限制，一般设为0或不设置，不限制大小
//        int prefecthSize = 0;
//        // 不要同时给消费端推送n条消息，一旦有n个消息还没ack，则该consumer将block掉，直到有ack 注意在自动应答下不生效
//        int prefetchCount = 30;
//        // 表示是否应用于channel上，即是channel级别还是consumer级别
//        boolean global = false;
//        channel.basicQos(prefecthSize, prefetchCount, global);
//        LogUtils.info("消费消息2:{}", body);
//
//        try {
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        int status = 0;
//        try {
//            //具体业务处理...
//
//        } catch (Exception e) {
//            status = 1;
//            LogUtils.error("业务逻辑处理错误");
//        } finally {
//            if (status == 0) {
//                //成功消费
//                channel.basicAck(tag, false);
//            } else {
//                //消费失败, 丢弃消息
//                channel.basicNack(tag, false, false);
//                //消费失败, 将消息返回队列末尾, 重复消费
////                channel.basicNack(tag, false, true);
//            }
//        }
//    }
//}
