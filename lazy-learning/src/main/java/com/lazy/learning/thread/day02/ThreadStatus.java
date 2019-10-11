package com.lazy.learning.thread.day02;

import lombok.extern.slf4j.Slf4j;

/**
 * @author futao
 * Created on 2019-05-29.
 */
@Slf4j
public class ThreadStatus implements Runnable {

    @Override
    public void run() {
        log.info("thread running");
        log.info("线程运行中的线程状态为:[{}]", Thread.currentThread().getState());
    }


    public static void main(String[] args) {
        ThreadStatus threadStatus = new ThreadStatus();


        Thread thread = new Thread(threadStatus);
        log.info("new Thread(thread)之后的线程状态:[{}]", thread.getState());
        thread.start();
        log.info("线程运行结束的线程状态为:[{}]", Thread.currentThread().getState());
    }
}
