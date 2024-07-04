package com.rongji.rjsoft.web.practice;

import java.util.concurrent.*;

/**
 * @description: 使用两个线程模拟消息的收发(通过Thread)
 * @author: JohnYehyo
 * @create: 2024-07-04 10:25:13
 */
public class NotificationSystem {

    private static final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<String> receiverQueue = new LinkedBlockingQueue<>();

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    private static final int NOTIFICATION_COUNT = 3;


    public static void main(String[] args) {
        CountDownLatch cd = new CountDownLatch(NOTIFICATION_COUNT);

//        Thread thread1 = new Thread(new Sender(senderQueue, receiverQueue, cd));
//        Thread thread2 = new Thread(new Receiver(receiverQueue, cd));
//        thread1.start();
//        thread2.start();

        //通过ExecutorService 来管理线程池, 避免上面显式创建线程
        executor.execute(new Sender(senderQueue, receiverQueue, cd));
        executor.execute(new Receiver(receiverQueue, cd));

        for (int i = 0; i < 5; i++) {
            try {
                senderQueue.put("消息" + i);
//                cd.await();
//
//                thread1.stop();
//                thread2.stop();
//
//                thread1.join();
//                thread2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}