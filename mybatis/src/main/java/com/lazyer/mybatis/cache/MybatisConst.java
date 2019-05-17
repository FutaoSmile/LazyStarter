package com.lazyer.mybatis.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author futao
 * Created on 2019-05-17.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "lazyer.mybatis")
@Component
public class MybatisConst {
    public static long mybatisRedisCacheExpireTimeInMinutes = 5L;

}
