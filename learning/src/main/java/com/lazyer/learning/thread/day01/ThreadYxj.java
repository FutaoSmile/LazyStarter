package com.lazyer.learning.thread.day01;

/**
 * 线程优先级
 *
 * @author futao
 * Created on 2019-05-27.
 */
public class ThreadYxj extends Thread {
    @Override
    public void run() {
        System.out.println("当前线程的优先级为:" + this.getPriority());
        this.setPriority(Thread.MAX_PRIORITY);
        System.out.println("当前线程的优先级为:" + this.getPriority());
    }

    public static void main(String[] args) {
        ThreadYxj thread = new ThreadYxj();
        thread.start();
    }
}