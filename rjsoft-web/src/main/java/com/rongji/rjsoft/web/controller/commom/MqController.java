package com.rongji.rjsoft.web.controller.commom;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.QueueEnum;
import com.rongji.rjsoft.web.component.RabbitMqSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2022-01-07 15:36:09
 */
@Api(tags = "消息队列")
@RestController
@RequestMapping(value = "mq")
public class MqController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @ApiOperation(value = "发送消息")
    @PostMapping(value = "sendMessage")
    public void sendMessage(String message) {
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey(), message);
        LogUtils.info("发送消息:{}", message);
    }

    @ApiOperation(value = "发送延迟消息")
    @PostMapping(value = "sendTtlMessage")
    public void sendTtlMessage(Long orderId, final long delayTimes) {
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        LogUtils.info("发送消息:{}", orderId);
    }

    @ApiOperation(value = "发送带有回复的消息")
    @PostMapping(value = "sendAckMessage")
    public void sendAckMessage(@RequestParam Object message) {

        rabbitMqSender.submitConfirm("test-ex",
                "test-key",
                JSON.toJSONString(message),
                UUID.fastUUID().toString());

        rabbitMqSender.submitConfirm("test-ex",
                "test-key222",
                JSON.toJSONString(message),
                UUID.fastUUID().toString());
    }

    @ApiOperation(value = "消息接收数量控制")
    @PostMapping(value = "sendMessageByLimitReceive")
    public void sendMessageByLimitReceive(@RequestParam Object message) {

        for (int i = 0; i < 15; i++) {
            rabbitMqSender.submitConfirm("test-ex",
                    "test-key",
                    String.valueOf(i),
                    UUID.fastUUID().toString());
        }
    }
}
