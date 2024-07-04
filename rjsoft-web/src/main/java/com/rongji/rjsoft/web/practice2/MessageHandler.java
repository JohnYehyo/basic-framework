package com.rongji.rjsoft.web.practice2;

import java.util.concurrent.*;

/**
 * @description: 使用两个线程模拟消息的收发(通过Completable)
 * @author: JohnYehyo
 * @create: 2024-07-04 11:10:05
 */
public class MessageHandler {

    private static final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<String> receiverQueue = new LinkedBlockingQueue<>();

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);
    public static void main(String[] args) {
        Sender sender = new Sender(senderQueue, receiverQueue);
        Receiver receiver = new Receiver(receiverQueue);

        CompletableFuture.runAsync(sender::action, executorService);
        CompletableFuture.runAsync(receiver::action, executorService);


        for (int i = 0; i < 5; i++) {
            try {
                senderQueue.put("message" + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Shutting down...");
            executorService.shutdown();
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("Shutdown complete.");
        }));
    }
}