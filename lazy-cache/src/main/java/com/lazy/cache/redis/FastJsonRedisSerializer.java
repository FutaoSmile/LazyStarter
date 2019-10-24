package com.lazy.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * 自定义Redis序列化,对于redisTemplate.opsForValue.set()有效，对注解@Cache无效，因为@Cache注解使用的是RedisTemplate<Object.Object>，
 * --可以自定义RedisCacheManager，并将redisTemplate设置成自定义的序列化工具，然后再@Cache()中使用这个自定义的RedisCacheManager
 *
 * @author futao
 * Created on 2019-03-22.
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    /**
     * 仅仅用作识别JSON.parseObject(text,class)方法
     */
    private Class<T> clazz = null;

    private static final SerializerFeature[] SERIALIZER_FEATURES = new SerializerFeature[]{
            SerializerFeature.PrettyFormat
            , SerializerFeature.SkipTransientField
//            , SerializerFeature.WriteEnumUsingName
//            , SerializerFeature.WriteDateUseDateFormat
            , SerializerFeature.WriteNullStringAsEmpty
            , SerializerFeature.WriteNullListAsEmpty
            , SerializerFeature.WriteMapNullValue
            // 【重点】序列化的时候必须需要带上Class类型，否则反序列化的时候无法知道Class类型
            , SerializerFeature.WriteClassName
    };

    /**
     * 序列化
     *
     * @param t 数据
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        return t == null ? null : JSON.toJSONString(t, SERIALIZER_FEATURES).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 反序列化
     * clazz为null也可以反序列化成功是因为对象在序列化的时候保存了对象的class
     *
     * @param bytes 字节数组
     * @return
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), clazz);
    }
}
