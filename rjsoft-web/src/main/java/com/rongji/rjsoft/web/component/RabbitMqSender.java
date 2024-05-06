package com.rongji.rjsoft.web.component;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    @Autowired
    private AllMsgCofirmCallback allMsgCofirmCallback;

    @Autowired
    private AllMsgReturnsCallback allMsgReturnsCallback;

    /**
     * 发送消息
     * @param exchange
     * @param routingKey
     * @param obj
     * @param baseID
     */
    public void submitConfirm(String exchange, String routingKey, Object obj, String baseID) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(baseID);

        //todo 记录消息发送
        LogUtils.info("[统一消息]接收到待投递消息:{}, 消息内容略去", baseID);
        try {
            rabbitTemplate.setMandatory(true);
            rabbitTemplate.setReturnsCallback(allMsgReturnsCallback);
            rabbitTemplate.setConfirmCallback(allMsgCofirmCallback);
            rabbitTemplate.convertAndSend(exchange, routingKey, obj, correlationData);
//            rabbitTemplate.convertAndSend(routingKey, obj, correlationData);
        } catch (Exception e) {
            LogUtils.error("[统一消息][发送端]消息:{}发送异常:", baseID, e.getMessage(), e);
        }
    }

    /**
     * 发送延迟消息
     * @param exchange
     * @param routingKey
     * @param obj
     * @param baseID
     * @param delayTimes
     */
    public void submitDelay(String exchange, String routingKey, Object obj, String baseID, long delayTimes) {
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(baseID);
        /**
         * 在 RabbitMQ 中，spring_returned_message_correlation 不会出现在死信队列以及死信队列转投的队列中。
         * 这是因为 spring_returned_message_correlation 是由 Spring AMQP 在发送消息时自动设置的，用于跟踪消息的相关信息
         * 所以可以使用
         * MessageProperties properties = new MessageProperties();
         *             properties.setHeader("messageType", "ttl");
         *             properties.setHeader("correlationId", "123456");
         *             Message msg = new Message(JSON.toJSONBytes(obj), properties);
         * 这样的消息的属性或者额外的标识来跟踪消息的消费情况, 而非上面注释掉的方法
         */
        MessageProperties properties = new MessageProperties();
        properties.setHeader("messageType", "ttl");
        properties.setHeader("correlationId", baseID);
        Message msg = new Message(JSON.toJSONBytes(obj), properties);

        //todo 记录消息发送
        LogUtils.info("[统一消息]接收到待投递延迟消息:{}, 消息内容略去, 时间:{}", baseID, LocalDateTime.now());
        try {
            rabbitTemplate.setMandatory(true);
            rabbitTemplate.setReturnsCallback(allMsgReturnsCallback);
//            rabbitTemplate.setConfirmCallback(allMsgCofirmCallback);
            rabbitTemplate.convertAndSend(exchange, routingKey, msg, message -> {
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            });
        } catch (Exception e) {
            LogUtils.error("[统一消息][发送端]消息:{}发送异常:", baseID, e.getMessage(), e);
        }
    }
}
