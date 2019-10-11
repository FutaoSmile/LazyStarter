package com.lazy.learning.thread.practice;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author futao
 * Created on 2019-06-03.
 */
public class ThreadTest {

    @Test
    public void test1() {
        Print print = new Print();
        new Thread(print, "小李>>>>>>>>").start();
        new Thread(print, "小王").start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test2() {
        FutureTask<String> futureTask = new FutureTask<>(new CallThreadTests());
        new Thread(futureTask).start();
        try {
            System.out.printf(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        System.out.println(DigestUtils.md5Hex("123456789"));
    }
}


class CallThreadTests implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "i am call()";
    }
}

class Print implements Runnable {

    int num = 100;

    @Override
    public void run() {
        print();
    }


    public synchronized void print() {
        while (true) {
            notify();//唤醒一个线程
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "正在打印第" + (--num) + "条信息");
                try {
                    wait();//释放同步锁，并将自己阻塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}