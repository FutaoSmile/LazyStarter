package com.lazy.cache.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author futao
 * Created on 2019/10/24.
 */
@Configuration
public class RedisConfig {
    /**
     * 自定义序列化
     * 这里的FastJsonRedisSerializer引用的自己定义的
     * 不自定义的话redisTemplate会乱码
     */
    @Primary
    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
        //redis反序列化 开启fastJson反序列化的autoType
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer<T>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        return redisTemplate;
    }

    @Primary
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb
                    .append(target.getClass().getSimpleName())
                    .append(":")
                    .append(method.getName());
            for (Object param : params) {
                sb
                        .append(":")
                        .append(param);
            }
            return sb.toString();
        };
    }

}
