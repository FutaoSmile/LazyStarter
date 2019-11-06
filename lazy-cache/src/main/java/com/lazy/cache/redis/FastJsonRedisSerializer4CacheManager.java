package com.lazy.cache.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 自定义的使用FastJson来序列化与反序列化-用于RedisCacheManager缓存管理器
 *
 * @author futao
 * Created on 2019/10/24.
 */
public class FastJsonRedisSerializer4CacheManager<T> implements RedisSerializer<T> {

    private final FastJsonRedisSerializer<T> fastJsonRedisSerializer = new FastJsonRedisSerializer<>();

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return fastJsonRedisSerializer.serialize(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return fastJsonRedisSerializer.deserialize(bytes);
    }
}
