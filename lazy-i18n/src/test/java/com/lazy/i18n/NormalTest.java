package com.lazy.i18n;

import org.junit.Test;

import java.util.Locale;

/**
 * @author futao
 * Created on 2019/11/2.
 */
public class NormalTest {

    @Test
    public void i18n01() {
        ResourceManager zh = ResourceManager.getInstance("i18n/i18n");
        System.out.println(zh.getString("hello"));
        ResourceManager en = ResourceManager.getInstance("i18n/i18n", Locale.ENGLISH);
        System.out.println(en.getString("hello"));
    }
}
