package com.lazy.learning.thread.day02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author futao
 * Created on 2019-05-28.
 */
@Slf4j
public class ByLock implements Runnable {

    private int ticket = 100;

    private ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (ticket > 0) {
                log.info("售票员{}卖出第{}张票", Thread.currentThread().getName(), ticket);
                ticket--;
            } else {
                break;
            }
            lock.unlock();
        }
        lock.unlock();
    }

    public static void main(String[] args) {

        ByLock byLock = new ByLock();

        Thread t1 = new Thread(byLock);
        Thread t2 = new Thread(byLock);
        Thread t3 = new Thread(byLock);

        t1.setName("【上海】");
        t2.setName("【杭州】");
        t3.setName("【广州】");

        t1.start();
        t2.start();
        t3.start();
    }
}
