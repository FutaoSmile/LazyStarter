package com.lazy.learning.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author futao
 * @date 2020/2/6.
 */
@ConditionalOnProperty(prefix = "switch", name = "xxl.job", havingValue = "on", matchIfMissing = false)
@Configuration
public class Condition {

    @Bean
    public String b() {
        System.out.println("0---------------加载了..........Condition");
        return "";
    }
}
