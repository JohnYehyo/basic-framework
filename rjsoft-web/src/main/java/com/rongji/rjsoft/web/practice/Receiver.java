package com.rongji.rjsoft.web.practice;

import com.rongji.rjsoft.common.util.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2024-07-04 10:27:26
 */
public class Receiver extends Thread{
    private final BlockingQueue<String> receiverQueue;
    private CountDownLatch countDownLatch;

    public Receiver(BlockingQueue<String> receiverQueue, CountDownLatch countDownLatch) {
        this.receiverQueue = receiverQueue;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        String message = null;
        while (true) {
            try {
                message = receiverQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            LogUtils.info("接收信息:{}", message);
        }

    }

//    public void stop() {
//        running = false;
//    }


}