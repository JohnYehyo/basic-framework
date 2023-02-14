package com.rongji.rjsoft.web.component;

import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: Rabbitmq消息发布
 * @author: JohnYehyo
 * @create: 2022-01-13 11:01:20
 */
@Component
public class RabbitMqSender {

    public static final String SPRING_RETURNED_MESSAGE_CORRELATION = "spring_returned_message_correlation";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void submitConfirm(String exchange, String routingKey, Object obj, String baseID) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(baseID);
        try {
            /**
             * 消息发送成功的回调当消息进入Exchange交换器时就进入回调,不管是否进入队列
             */
            rabbitTemplate.setConfirmCallback((correlation, ack, cause) -> {
                System.out.println(ack);
                if (!ack) {
                    LogUtils.error("消息发送到broker失败,原因: {}", cause);
                }else{
                    System.out.println("发送记录标记更新");
                }
            });
            /**
             * 当消息进入Exchange交换器时但是未进入队列时回调
             *
             * Mandatory两种状态：
             * 为true时,消息通过交换器无法匹配到队列会返回给生产者 并触发MessageReturn
             * 为false时,匹配不到会直接被丢弃
             */
            rabbitTemplate.setReturnsCallback(returnCallback -> {
                String correlationId = returnCallback.getMessage().getMessageProperties().getHeader(SPRING_RETURNED_MESSAGE_CORRELATION);
                int replyCode = returnCallback.getReplyCode();
                String replyText = returnCallback.getReplyText();
                String ex = returnCallback.getExchange();
                String rk = returnCallback.getRoutingKey();
                System.out.println("correlationId:" + correlationId);
                LogUtils.error("消息：{} 发送, 应答码：{} 原因：{} 交换机: {}  路由键: {}", returnCallback.getMessage(), replyCode, replyText, ex, rk);
            });
            rabbitTemplate.setMandatory(true);
            rabbitTemplate.convertAndSend(exchange, routingKey, obj, correlationData);
//            rabbitTemplate.convertAndSend(routingKey, obj, correlationData);
        } catch (Exception e) {
            LogUtils.error("消息发送异常:" + e.getMessage(), e);
        }
    }
}
