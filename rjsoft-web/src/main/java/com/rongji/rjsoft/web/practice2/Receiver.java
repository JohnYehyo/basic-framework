package com.rongji.rjsoft.web.practice2;

import com.rongji.rjsoft.common.util.LogUtils;

import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2024-07-04 11:31:05
 */
public class Receiver {

    private final BlockingQueue<String> receiverQueue;

    public Receiver(BlockingQueue<String> receiverQueue) {
        this.receiverQueue = receiverQueue;
    }

    public void action(){
        while(true){
            String message = null;
            try {
                message = receiverQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            LogUtils.info("接收信息:{}", message);
        }
    }
}