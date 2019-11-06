### # api-generator[不完整]

> 根据swagger-api生成markdown文档。markdown可再导出为pdf，html等
* 设计思路：
    * 第一版是直接边解析swagger数据，边生成md文档的，这样导致生成md文档的代码与swagger的解析代码高度耦合在一起，如果需要需要更换数据源，比如不使用swagger了，使用另外一种数据源，那么整个代码基本就废了。
    * 所以第二版将整个过程分成了两步
        * 第一步为解析swagger数据，将数据封装到自己的数据结构当中
        * 第二步根据封装好的数据解析成md文档
    * 所以如果更换了数据源，只需要重新编写读取数据源解析为通用的定义的数据即可，根据数据生成md文档的代码不需要动。
* **注意:** 该组件目前依赖swaggerApi，所以你的项目需要先依配置好swagger，并且启动好项目。
* 使用方法
    * 引入依赖
    ```xml
      <dependency>
          <groupId>com.lazy.starter</groupId>
          <artifactId>api-generator</artifactId>
          <version>${project.lazy.version}</version>
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
* Todo
    * 提供不依赖于swagger的生成方案
    * ~~使用fastjson解析JsongString时，key为`$ref`的value会一直循环引用~~,[issue->fastjson](https://github.com/alibaba/fastjson/issues/2429)
    * 直接生成html/pdf等