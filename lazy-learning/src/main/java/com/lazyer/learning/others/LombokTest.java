package com.lazyer.learning.others;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author futao
 * Created on 2019-05-31.
 */
public class LombokTest {

    @Test
    public void test1() {
        try {
//            @Cleanup
            FileInputStream fileInputStream = new FileInputStream(new File("55.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}