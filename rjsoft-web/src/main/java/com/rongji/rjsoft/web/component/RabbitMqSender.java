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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void submitConfirm(String exchange, String routingKey, Object obj, String baseID) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(baseID);
        try {
            rabbitTemplate.setConfirmCallback((correlation, ack, cause) -> {
                if (!ack) {
                    LogUtils.error("消息发送到broker失败,原因: {}", cause);
                }
            });
            rabbitTemplate.setReturnCallback((message, replyCode, replyText, ex, rk) -> {
                String correlationId = message.getMessageProperties().getCorrelationId();
                LogUtils.error("消息：{} 发送, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, ex, rk);
            });
            rabbitTemplate.setMandatory(true);
            rabbitTemplate.convertAndSend(exchange, routingKey, obj, correlationData);
        } catch (Exception e) {
            LogUtils.error("消息发送异常:" + e.getMessage(), e);
        }
    }
}
