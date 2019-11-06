### # 缓存组件
* 此模块教程：
    * [[原创]SpringBoot的自动配置原理与自定义SpringBootStarter](https://www.jianshu.com/p/d8c0dab9b56e)
    * [[原创]SpringBoot 2.x Redis缓存乱码问题/自定义SpringBoot-Cache序列化方式](https://www.jianshu.com/p/9b20a958a34b)
#### 1. 使用方法
* 添加依赖即可
```xml
        <artifactId>lazy-cache</artifactId>
        <groupId>com.lazy.starter</groupId>
        <version>1.2.3</version>
```
* 实现了与SpringStarter一样的自动装配功能，只需要引入依赖即可使用。

#### 2. 实现的功能
* `FastJsonRedisSerializer.class`实现了`RedisTemplate<String,T>`的序列化与反序列化，杜绝乱码的出现。且可直接指定value的类型，存储与查询不再需要将对象转换成String再转换成对象，使用起来非常方便。
    eg:
    ```java
        @Resource
        private RedisTemplate<String, User> redisTemplate;
  
      //存储
         User user = new User();
         user.setUserName("futao");
         redisTemplate.opsForValue().set("1", user);
  
      //查询
      User user = redisTemplate.opsForValue().get("1");
    ```
  ![image.png](https://upload-images.jianshu.io/upload_images/1846623-13bf1cd9bd443343.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  
* `FastJsonRedisSerializer4CacheManager.class`实现了RedisCacheManager-Redis缓存管理器的使用FastJson作为自定义的序列化/反序列化工具，杜绝的乱码的产生。
    通过RedisConfig.determineConfiguration()方法，将JDK的序列化方式改为FastJsonRedisSerializer4CacheManager