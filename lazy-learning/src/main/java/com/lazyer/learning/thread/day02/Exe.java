package com.lazyer.learning.thread.day02;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 练习题：
 * 两个客户同时向同一个账户分三次，每次存入1000元
 *
 * @author futao
 * Created on 2019-05-28.
 */
@Slf4j
public class Exe implements Runnable {

    private Account account;

    public Exe(Account account) {
        this.account = account;
    }

    private ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            lock.lock();
            int balance = account.getBalance();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account.setBalance(balance + 1000);
            lock.unlock();
            log.info("余额为：{}", account.getBalance());
        }
    }

    public static void main(String[] args) {
        Exe exe = new Exe(new Account(2000));

        Thread t1 = new Thread(exe);
        Thread t2 = new Thread(exe);

        t1.start();
        t2.start();

    }
}


@Getter
@Setter
@AllArgsConstructor
class Account {
    private int balance;
}
