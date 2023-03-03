package com.rongji.rjsoft.web.controller.commom;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.QueueEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.vo.ResponseVo;
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
    public ResponseVo sendMessage(String message) {
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey(), message);
        LogUtils.info("发送消息:{}", message);
        return ResponseVo.success();
    }

    @ApiOperation(value = "发送延迟消息")
    @PostMapping(value = "sendTtlMessage")
    public ResponseVo sendTtlMessage(Long orderId, final long delayTimes) {
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        LogUtils.info("发送消息:{}", orderId);
        return ResponseVo.success();
    }

    @ApiOperation(value = "发送带有回复的消息")
    @PostMapping(value = "sendAckMessage")
    public ResponseVo sendAckMessage(@RequestParam Object message) {

        rabbitMqSender.submitConfirm("test-ex",
                "test-key",
                JSON.toJSONString(message),
                UUID.fastUUID().toString());

        rabbitMqSender.submitConfirm("test-ex",
                "test-key222",
                JSON.toJSONString(message),
                UUID.fastUUID().toString());

        return ResponseVo.success();
    }

    @ApiOperation(value = "多个队列且队列并发控制")
    @PostMapping(value = "sendMessageByLimitReceive")
    public ResponseVo sendMessageByLimitReceive(@RequestParam Object message) {

        for (int i = 0; i < 15; i++) {
            rabbitMqSender.submitConfirm("test-ex",
                    "test-key",
                    String.valueOf(i),
                    UUID.fastUUID().toString());
        }
        return ResponseVo.success();
    }

    @ApiOperation(value = "通配符接收消息")
    @PostMapping(value = "sendMessageByTopicReceive")
    public ResponseVo sendMessageByTopicReceive(@RequestParam Object message) {

        //通配符ak.#接收
        for (int i = 0; i < 5; i++) {
            rabbitMqSender.submitConfirm("am-topic-ex",
                    "ak.test",
                    "ak:" + i,
                    UUID.fastUUID().toString());
        }

        //通配符am.#接收
        for (int i = 0; i < 5; i++) {
            rabbitMqSender.submitConfirm("am-topic-ex",
                    "am.test",
                    "am:" + i,
                    UUID.fastUUID().toString());
        }
        return ResponseVo.success();
    }

    @ApiOperation(value = "多个队列同时接收消息")
    @PostMapping(value = "sendMessageByAllReceive")
    public ResponseVo sendMessageByAllReceive(@RequestParam Object message) {

        for (int i = 0; i < 5; i++) {
            rabbitMqSender.submitConfirm("am-fanout-ex",
                    "test-key",
                    String.valueOf(i),
                    UUID.fastUUID().toString());
        }
        return ResponseVo.success();
    }
}
