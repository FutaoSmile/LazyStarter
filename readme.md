## 懒人组件库-公共组件

> 为避免每个项目都需要重复编写的部分代码，现在提出来作为通用的代码组件库，该组件库根据不同的功能模块，将组件拆分成了一个个小模块。其他项目只需要导入相应的基础组件就可以了。方便管理，便于开发。

- 使用方法
  【安装】将本项目clone到本地([https://github.com/FutaoSmile/CommonFramework](https://github.com/FutaoSmile/CommonFramework))，执行`mvn clean install -Dmaven.test.skip=true`即可将组件安装到本地maven仓库。

  【在`maven`中使用】

  ```xml
      <properties>
          <project.lazy.version>1.1.2</project.lazy.version>
      </properties>
  
      <dependency>
          <groupId>com.lazy.starter</groupId>
          <artifactId>http-client</artifactId>
          <version>${project.lazy.version}</version>
      </dependency>
  
      <dependency>
           <groupId>com.lazy.starter</groupId>
          <artifactId>api-generator</artifactId>
          <version>${project.lazy.version}</version>
      </dependency>
  ```

  【在`gradle`中使用】需要先设置为项目设置从本地拉取maven依赖

  ```gradle
  buildscript {
      ext {
      	lazyVersion = '1.1.2'
      }
  
  repositories {
   		//从本次maven库中找
      mavenLocal()
      //从maven中央仓库找
      mavenCentral()
  }
  
  //依赖
  dependencies {
  //通用模块组件依赖
      compile "com.lazy.starter:http-client:${lazyVersion}"
      compile "com.lazy.starter:foundation:${lazyVersion}"
      compile "com.lazy.starter:api-generator:${lazyVersion}"
  }
  
  ```
  
### # [api-generator](https://github.com/FutaoSmile/lazyStarter/tree/master/api-generator)

> 根据swagger-api生成markdown文档。markdown可再导出为pdf，html等

### # [http-client](https://github.com/FutaoSmile/lazyStarter/tree/master/http-client)
> http工具包

### # http-client
* 使用方法
```java
public class Test{
        @Test
        public void test89() {
            AbstractBaseRequest request = new GetRequest("http://localhost:8887/user/list");
            request.addCredentials("admin", "admin");
            request.addParameter("pageNum", "1");
            request.addParameter("pageSize", "100");
            String result = request.send();
            System.out.println(result);
        }
}
```
![image.png](https://upload-images.jianshu.io/upload_images/1846623-8b26a644e59cc546.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### # [lazy-i18n](https://github.com/FutaoSmile/LazyStarter/tree/master/lazy-i18n)
> 国际化模块，可参考aliOss的实现
```xml
        <dependency>
            <groupId>com.aliyun.oss</groupId>
            <artifactId>aliyun-sdk-oss</artifactId>
            <version>2.8.3</version>
        </dependency>
```

### # [mybatis](https://github.com/FutaoSmile/lazyStarter/tree/master/mybatis)

> mybatis二级缓存

### # [tools](https://github.com/FutaoSmile/lazyStarter/tree/master/tools) 

> 工具包

* 读取Excel
* 写入Excel

### # [lazy-admin](https://github.com/FutaoSmile/LazyStarter/tree/master/lazy-admin)
* SpringBoot-Admin的使用-服务端
* 使用SpringSecurity保证SpringBootAdminServer的安全
### # [lazy-cache](https://github.com/FutaoSmile/LazyStarter/tree/master/lazy-cache)
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
    