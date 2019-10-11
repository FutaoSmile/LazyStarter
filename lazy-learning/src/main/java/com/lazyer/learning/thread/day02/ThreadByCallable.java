package com.lazyer.learning.thread.day02;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author futao
 * Created on 2019-05-30.
 */
public class ThreadByCallable implements Callable<Integer> {//实现Callable<Integer>接口，泛型为返回值类型

    @Override
    public Integer call() throws Exception {//重写call方法
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + "   :" + i);
                sum += i;
            }
        }
        return sum;//将结果返回
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FutureTask<Integer> futureTask = new FutureTask<>(new ThreadByCallable());
        new Thread(futureTask).start();//启动线程
        try {
            System.out.println("isDone" + futureTask.isDone());
            System.out.println("结果为" + futureTask.get());//获取到线程的返回值
            System.out.println("结果为" + futureTask.get());//获取到线程的返回值
            System.out.println("isDone" + futureTask.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long resultTime = System.currentTimeMillis() - start;
    }
}
