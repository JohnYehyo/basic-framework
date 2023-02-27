package com.rongji.rjsoft.web.component;

import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @description: 消息未入队列回调
 * @author: JohnYehyo
 * @create: 2023-02-15 11:18:33
 */
@Component
public class AllMsgReturnsCallback implements RabbitTemplate.ReturnsCallback {

    public static final String SPRING_RETURNED_MESSAGE_CORRELATION = "spring_returned_message_correlation";

    /**
     * 当消息进入Exchange交换器时但是未进入队列时回调
     *
     * Mandatory两种状态：
     * 为true时,消息通过交换器无法匹配到队列会返回给生产者 并触发MessageReturn
     * 为false时,匹配不到会直接被丢弃
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
//        String correlationId = returnedMessage.getMessage().getMessageProperties().getHeader(SPRING_RETURNED_MESSAGE_CORRELATION);
        int replyCode = returnedMessage.getReplyCode();
        String replyText = returnedMessage.getReplyText();
        String ex = returnedMessage.getExchange();
        String rk = returnedMessage.getRoutingKey();
        LogUtils.error("[统一消息][RETURN回调]消息：{} 发送, 应答码：{} 原因：{} 交换机: {}  路由键: {}", returnedMessage.getMessage(), replyCode, replyText, ex, rk);
    }
}