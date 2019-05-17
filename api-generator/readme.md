### # api-generator

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
