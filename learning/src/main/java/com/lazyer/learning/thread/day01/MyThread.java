package com.lazyer.learning.thread.day01;

import lombok.SneakyThrows;

/**
 * 实现多线程的第一种方式-继承Thread
 *
 * @author futao
 * Created on 2019-05-27.
 */
public class MyThread extends Thread {
    @SneakyThrows
    @Override
    public void run() {
        System.out.println("得得得");
    }

    @SneakyThrows
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();

    }
}