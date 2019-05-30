package com.lazyer.learning.xiaohui.day02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author futao
 * Created on 2019-05-28.
 */
@Slf4j
public class Ticket implements Runnable {
    private int ticket = 100;

    //公平锁
    Lock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            lock.lock();//获取锁
            if (ticket > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket--;
                log.info("{}卖出了第{}张票", Thread.currentThread().getName(), 100 - ticket);
                lock.unlock();//释放锁
            } else {
                lock.unlock();//释放锁
                break;
            }
        }
    }

    public static void main(String[] args) {
        Ticket t1 = new Ticket();
        new Thread(t1).start();
        new Thread(t1).start();
        new Thread(t1).start();
    }
}
