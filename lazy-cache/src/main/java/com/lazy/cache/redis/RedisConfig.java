package com.lazy.cache.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Redis配置
 *
 * @author futao
 * Created on 2019/10/24.
 */
@Configuration
@Order
@AutoConfigureAfter({CacheAutoConfiguration.class})
@Import({CacheAutoConfiguration.class})
public class RedisConfig {

    private final CacheProperties cacheProperties;

    private final CacheManagerCustomizers customizerInvoker;

    private final RedisCacheConfiguration redisCacheConfiguration;


    public RedisConfig(CacheProperties cacheProperties,
                       CacheManagerCustomizers customizerInvoker,
                       ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration) {
        this.cacheProperties = cacheProperties;
        this.customizerInvoker = customizerInvoker;
        this.redisCacheConfiguration = redisCacheConfiguration.getIfAvailable();
    }

    /**
     * 自定义序列化
     * 这里的FastJsonRedisSerializer引用的自己定义的
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

    /**
     * key的生成规则
     *
     * @return
     */
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

    /**
     * Redis缓存管理器
     *
     * @param redisConnectionFactory
     * @param resourceLoader
     * @return
     */
    @Primary
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
                                          ResourceLoader resourceLoader) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(determineConfiguration(resourceLoader.getClassLoader()));
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        }
        return this.customizerInvoker.customize(builder.build());
    }


    /**
     * 读取redisCache配置与设置序列化反序列化
     *
     * @param classLoader
     * @return
     */
    private RedisCacheConfiguration determineConfiguration(
            ClassLoader classLoader) {
        if (this.redisCacheConfiguration != null) {
            return this.redisCacheConfiguration;
        }
        CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig();
        //指定采用的序列化工具
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new FastJsonRedisSerializer4CacheManager<>()));
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }


}
