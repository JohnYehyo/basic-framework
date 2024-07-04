package com.rongji.rjsoft.web.practice3;

import com.rongji.rjsoft.common.util.LogUtils;

import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2024-07-04 11:31:05
 */
public class Receiver {

    private final BlockingQueue<String> receiverQueue;

    private volatile boolean running = true;

    public Receiver(BlockingQueue<String> receiverQueue) {
        this.receiverQueue = receiverQueue;
    }

    public void action(){
        while(running){
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

    public void stop(){
        running = false;
    }
}