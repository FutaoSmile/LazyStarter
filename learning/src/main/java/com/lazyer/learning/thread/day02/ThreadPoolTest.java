package com.lazyer.learning.thread.day02;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author futao
 * Created on 2019-05-30.
 */
@Slf4j
public class ThreadPoolTest {

    @Test
    public void test() {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                2,
//                10,
//                60,
//                TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<>(10),
//                new ThreadFactory() {
//                    AtomicInteger num = new AtomicInteger(0);
//
//                    @Override
//                    public Thread newThread(Runnable r) {
//                        return new Thread(r, "threadPool-" + num.getAndIncrement());
//                    }
//                },
//                (r, executor1) -> log.info("完蛋{},{}", r, executor1)
//        );
//        CountDownLatch countDownLatch = new CountDownLatch(20);
//        for (int i = 0; i < 20; i++) {
//            executor.execute(() -> {
//                log.info("-");
//                //注意，一定要放在当前线程里面。如果放在execute()外面，则是主线程调用的countDown，还没等到线程执行完，所以是错误的，不会输出内容
//                countDownLatch.countDown();
//            });
//        }
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        executor.shutdown();


        ExecutorService service = Executors.newFixedThreadPool(10, new ThreadFactory() {
            AtomicInteger num = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "t-p-" + num.getAndIncrement());
            }
        });
        for (int i = 0; i < 100; i++) {
            Future<Integer> submit = service.submit(() -> {
                log.info("当前线程内部");
                return 100;
            });
            try {
                Integer integer = submit.get();
                log.info("" + integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();


        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                log.info("good");
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
//
//        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
//            AtomicInteger atomicInteger = new AtomicInteger(0);
//
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r, "s-t-s-e-" + atomicInteger.getAndIncrement());
//            }
//        });
//        CountDownLatch c = new CountDownLatch(10);
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            log.info("90");
//            c.countDown();
//        }, 1, 3, TimeUnit.SECONDS);
//        try {
//            c.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        scheduledExecutorService.shutdown();
    }
}
