## 懒人组件库-公共组件

> 为避免每个项目都需要重复编写的部分代码，现在提出来作为通用的代码组件库，该组件库根据不同的功能模块，将组件拆分成了一个个小模块。其他项目只需要导入相应的基础组件就可以了。方便管理，便于开发。

- 使用方法
  【安装】将本项目clone到本地([https://github.com/FutaoSmile/CommonFramework](https://github.com/FutaoSmile/CommonFramework))，执行`mvn clean install -Dmaven.test.skip=true`即可将组件安装到本地maven仓库。

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
  
### # [api-generator](https://github.com/FutaoSmile/LazyerStarter/tree/master/api-generator)

> 根据swagger-api生成markdown文档。markdown可再导出为pdf，html等

### # [http-client](https://github.com/FutaoSmile/LazyerStarter/tree/master/http-client)

> http工具包

### # [mybatis](https://github.com/FutaoSmile/LazyerStarter/tree/master/mybatis)

> mybatis二级缓存

### # [tools](https://github.com/FutaoSmile/LazyerStarter/tree/master/tools) 

> 工具包

* 读取Excel
* 写入Excel


### TOTO
* 基于注解的分布式锁