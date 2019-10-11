package com.lazy.mybatis.cache;

import com.lazy.rest.utils.SpringTools;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用redis作为mybatis的二级缓存
 * <p>
 * 规则：
 * 以mapper为单位进行存储，前缀为mybatis.${MapperDao}.****。
 * 所以删除的时候也以mapper为单位进行删除。
 * 对于删除：
 * 1.思路：先根据前缀通过keys操作获取keyList。==>因为redis是单线程的，所以不允许使用keys操作，会阻塞其他的线程获取redis操作。(可以考虑使用scan操作)
 * 2.思路：先将所有的mybatis二级缓存的key根据mapper进行分组存入redis的每个list中，删除的时候先从redis的list中获取对应mapper的所有的key
 *
 * @author futao
 * Created on 2019-03-06.
 */
@SuppressWarnings("unchecked")
public class MybatisCacheRedis implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(MybatisCacheRedis.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * cache instance id
     */
    private final String id;

    private RedisTemplate redisTemplate;

    public MybatisCacheRedis(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Put query result to redis
     *
     * @param key
     * @param value
     */
    @Override
    public void putObject(Object key, Object value) {
        if (key != null && value != null) {
            RedisTemplate redisTemplate = getRedisTemplate();
            String redisKey = RedisKeySet.mybatisSecondCacheMapperKey(id + ":" + key);
            //不要设置过期时间
            redisTemplate.opsForList().rightPush(RedisKeySet.mybatisSecondCacheListKey(id), redisKey);
            redisTemplate.opsForValue().set(redisKey, value, SpringTools.getBean(MybatisConst.class).mybatisRedisCacheExpireTimeInSecond, TimeUnit.SECONDS);
            logger.debug("\n<<< 结果插入redis缓存\n【key】{}\n【值】:{}", redisKey, value);
        }
    }

    /**
     * Get cached query result from redis
     *
     * @param key
     * @return
     */
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String redisKey = RedisKeySet.mybatisSecondCacheMapperKey(id + ":" + key);
        Object result = opsForValue.get(redisKey);
        logger.debug("\n<<< 从redis中查询缓存\n【key】{}\n【结果】:{}", redisKey, result);
        return result;
    }

    /**
     * Remove cached query result from redis
     *
     * @param key
     * @return
     */
    @Override
    public Object removeObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        String redisKey = RedisKeySet.mybatisSecondCacheMapperKey(id + ":" + key);
        logger.debug("\n<<< 从redis中移除缓存\n【key】{}", redisKey);
        //TODO(...)
        return redisTemplate.delete(redisKey);
    }

    /**
     * delete
     * Clears this cache instance
     */
    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
//        redisTemplate.execute((RedisCallback) connection -> {
////            connection.flushDb();
////            return null;
////        });

        String key = RedisKeySet.mybatisSecondCacheListKey(id);
        //从mapper list中获取mapper keys
        List range = redisTemplate.opsForList().range(key, 0, -1);
        //删除mapper list
        range.add(key);
        //删除mapper kv
        Long delete = redisTemplate.delete(range);
        logger.debug("\n<<< 删除redis中【{}】的缓存\n删除条数【{}】", key, delete);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = SpringTools.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}