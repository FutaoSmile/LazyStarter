### 公共组件

> 为避免每个项目都需要重复编写的部分代码，现在提出来作为通用的代码组件库，该组件库根据不同的功能模块，将组件拆分成了一个个小模块。其他项目只需要导入相应的基础组件就可以了。方便管理，便于开发。

- 使用方法
  【安装】将本项目clone到本地(https://github.com/FutaoSmile/CommonFramework)，执行`mvn clean install -Dmaven.test.skip=true`即可将组件安装到本地maven仓库。

  【在`maven`中使用】

  ```xml
  <properties>
     <project.lazyer.version>1.1.2</project.lazyer.version>
  </properties>
  
    <dependency>
        <groupId>com.lazyer.starter</groupId>
        <artifactId>http-client</artifactId>
        <version>${project.lazyer.version}</version>
    </dependency>
  
    <dependency>
        <groupId>com.lazyer.starter</groupId>
        <artifactId>api-generator</artifactId>
        <version>${project.lazyer.version}</version>
    </dependency>
  ```

  【在`gradle`中使用】需要先设置为项目设置从本地拉取maven依赖

  ```gradle
  buildscript {
      ext {
      	lazyerVersion = '1.1.2'
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
      compile "com.lazyer.starter:http-client:${lazyerVersion}"
      compile "com.lazyer.starter:foundation:${lazyerVersion}"
      compile "com.lazyer.starter:api-generator:${lazyerVersion}"
  }
  
  ```

  

#### # api-generator

> 根据swagger-api生成markdown文档。markdown可再导出为pdf，html等
* **注意:** 该组件目前依赖swaggerApi，所以你的项目需要先依配置好swagger，并且启动好项目。
* 使用方法
    * 引入依赖
    ```xml
      <dependency>
          <groupId>com.lazyer.starter</groupId>
          <artifactId>api-generator</artifactId>
          <version>${project.lazyer.version}</version>
      </dependency>    
    ```
    * 使用demo
    ![image.png](https://upload-images.jianshu.io/upload_images/1846623-748e4e040dd7c239.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    ```java
     public class Test{
        @Test
          public void test88() {
              ApiInfo apiInfo = ApiGenerator.gen("/Users/futao/Desktop/apiDoc.md", "http://localhost:8887/v2/api-docs");
              System.out.println(JSON.toJSONString(apiInfo, HttpMessageConverterConfiguration.SERIALIZER_FEATURES));
          }
      }
    ```
    ![image.png](https://upload-images.jianshu.io/upload_images/1846623-67ac1f318211603e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* feature
    * 生成接口文档的同时会将解析出的swagger数据结构同时返回。
    * 支持对使用了security的接口进行解析。
    * 支持同时加载多个数据源。

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
