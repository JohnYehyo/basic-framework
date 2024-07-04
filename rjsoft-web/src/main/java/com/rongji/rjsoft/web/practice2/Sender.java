package com.rongji.rjsoft.web.practice2;

import com.rongji.rjsoft.common.util.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2024-07-04 11:30:53
 */
public class Sender {

    private final BlockingQueue<String> senderQueue;
    private final BlockingQueue<String> receiverQueue;

    public Sender(BlockingQueue<String> senderQueue, BlockingQueue<String> receiverQueue) {
        this.senderQueue = senderQueue;
        this.receiverQueue = receiverQueue;
    }

    public void action(){
        while(true){
            String message = null;
            try {
                message = senderQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            LogUtils.info("发送信息:{}", message);
            try {
                receiverQueue.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public void stop(){

    }
}