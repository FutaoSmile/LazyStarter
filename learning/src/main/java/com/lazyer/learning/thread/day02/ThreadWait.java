package com.lazyer.learning.thread.day02;

/**
 * @author futao
 * Created on 2019-05-29.
 */
public class ThreadWait implements Runnable {
    int i = 0;

    @Override
    public void run() {
//               synchronized (this) {
        sync();
//        }
    }

    public synchronized void sync() {
        while (true) {
            notify();
            if (i <= 100) {
                System.out.println(Thread.currentThread().getName() + "   :" + ++i);
            } else {
                break;
            }
            try {
                wait();//释放锁，并进入阻塞状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadWait wait = new ThreadWait();
        new Thread(wait, "yoyoyo1").start();
        new Thread(wait, "yoyoyo2").start();
        new Thread(wait, "yoyoyo3").start();
    }
}
