package com.lazyer.learning.thread.day01;

/**
 * 实现多线程的第二种方式-实现Runnable接口
 *
 * @author futao
 * Created on 2019-05-27.
 */
public class RunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println("得得得");
    }


    public static void main(String[] args) {
        int ticket = 100;
        RunnableThread runnableThread = new RunnableThread();


        Thread thread1 = new Thread(runnableThread);
        thread1.start();

        Thread thread2 = new Thread(runnableThread);
        thread2.start();

        Thread thread3 = new Thread(runnableThread);
        thread3.start();

    }
}
