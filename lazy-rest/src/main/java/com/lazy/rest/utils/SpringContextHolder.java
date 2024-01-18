package com.lazy.rest.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring容器
 *
 * @author futao
 * Created on 2019-01-15.
 */

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }

    /**
     * 获取容器
     *
     * @return 容器
     */
    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }
}
