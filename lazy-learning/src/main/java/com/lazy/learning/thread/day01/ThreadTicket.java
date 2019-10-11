package com.lazy.learning.thread.day01;

/**
 * @author futao
 * Created on 2019-05-27.
 */
public class ThreadTicket extends Thread {

    private static int ticket = 100;
    private static int total = 100;

    public ThreadTicket(String name) {
        super(name);
    }

    @Override
    public void run() {
        int tot = 0;
        while (ticket > 0) {
            ticket--;
            System.out.println(getName() + "卖出了第" + (total - ticket) + "张票");
            tot++;
        }
        System.out.println(">>>>" + getName() + "一共卖出了" + tot + "张票");
    }


    public static void main(String[] args) {
        ThreadTicket s1 = new ThreadTicket("广东");
        ThreadTicket s2 = new ThreadTicket("上海");
        ThreadTicket s3 = new ThreadTicket("杭州东");

        s1.start();
        s2.start();
        s3.start();
    }
}
