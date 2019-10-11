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
