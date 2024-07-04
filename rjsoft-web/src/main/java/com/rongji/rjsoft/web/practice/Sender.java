package com.rongji.rjsoft.web.practice;

import com.rongji.rjsoft.common.util.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2024-07-04 10:27:04
 */
public class Sender implements Runnable{

    private BlockingQueue<String> senderQueue;
    private BlockingQueue<String> receiverQueue;

    private CountDownLatch countDownLatch;

    private volatile boolean running = true;

    public Sender(BlockingQueue<String> senderQueue, BlockingQueue<String> receiverQueue, CountDownLatch countDownLatch) {
        this.senderQueue = senderQueue;
        this.receiverQueue = receiverQueue;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (true) {
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

//    public void stop() {
//        running = false;
//    }
}