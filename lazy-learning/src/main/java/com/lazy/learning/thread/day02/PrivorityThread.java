package com.lazy.learning.thread.day02;

import lombok.extern.slf4j.Slf4j;

/**
 * @author futao
 * Created on 2019-05-29.
 */
@Slf4j
public class PrivorityThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            log.info(Thread.currentThread().getName() + " :" + i);
            Thread.currentThread().setDaemon(true);

        }
    }

    public static void main(String[] args) {
        PrivorityThread privorityThread = new PrivorityThread();
        Thread currentThread = Thread.currentThread();
        log.error(currentThread.getName());

        Thread t1 = new Thread(privorityThread);
        Thread t2 = new Thread(privorityThread);
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.setPriority(Thread.MIN_PRIORITY);
        t2.start();
    }
}
