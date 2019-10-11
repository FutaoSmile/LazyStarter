package com.lazyer.mybatis.cache;

/**
 * @author futao
 * Created on 2019-05-17.
 */
public class RedisKeySet {
    private static final String PREFIX = "lazyer:mybatis:second-level-cache";

    /**
     * mybatis的查询二级缓存key
     */
    public static String mybatisSecondCacheMapperKey(Object key) {
        return PREFIX + ":mapper:" + key.toString();
    }

    public static String mybatisSecondCacheListKey(Object key) {
        return PREFIX + ":list:" + key.toString();
    }
}
