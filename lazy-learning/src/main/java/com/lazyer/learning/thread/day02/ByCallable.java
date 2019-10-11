package com.lazyer.learning.thread.day02;

import com.lazyer.foundation.model.BaseEntity;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

/**
 * @author futao
 * Created on 2019-05-29.
 */
public class ByCallable implements Callable<BaseEntity> {
    @Override
    public BaseEntity call() throws Exception {
        return new BaseEntity("iiiiid", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }


    public static void main(String[] args) {
        ByCallable byCallable = new ByCallable();
        try {
            BaseEntity call = byCallable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
