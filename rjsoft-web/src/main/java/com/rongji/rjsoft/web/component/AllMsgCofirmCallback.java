package com.rongji.rjsoft.web.component;

import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @description: 消息发送成功回调
 * @author: JohnYehyo
 * @create: 2023-02-15 11:19:38
 */
@Component
public class AllMsgCofirmCallback implements RabbitTemplate.ConfirmCallback {

    /**
     * 消息发送成功的回调当消息进入Exchange交换器时就进入回调,不管是否进入队列
     * @param correlationData correlationData
     * @param ack ack
     * @param cause 失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();
        if (ack) {
//            LogUtils.info("[统一消息][COFIRM回调]消息:{}, 成功发送到exchange", id);
            //todo 更新记录表状态
        }else{
            LogUtils.error("[统一消息][COFIRM回调]消息:{}, 发送到broker失败,原因: {}", id, cause);
        }
    }
}