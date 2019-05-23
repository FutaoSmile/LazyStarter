package com.lazyer.mybatis.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mybatis组件配置
 *
 * @author futao
 * Created on 2019-05-17.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "lazyer.mybatis")
@Component
public class MybatisConst {
    /**
     * mybatis二级缓存过期时间(分钟)
     */
    public static long mybatisRedisCacheExpireTimeInMinutes = 5L;
    /**
     * 是否打印sql
     */
    public static boolean showSql = true;
    /**
     * 慢sql时间(毫秒)
     */
    public static long slowSqlMillis = 1000L;
}
