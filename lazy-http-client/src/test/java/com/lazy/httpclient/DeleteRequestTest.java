package com.lazy.httpclient;

import org.junit.Test;

/**
 * @author futao
 * Created on 2019-07-05.
 */
public class DeleteRequestTest {

    @Test
    public void send() {
//        PutRequest request = new PutRequest("https://ec-service-dev.byton.cn/api-user/v1/cms/admin-user/login");
//        request.addEntity("{\"loginId\":\"admin\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"code\":\"4nnx\",\"loginType\":1,\"state\":1,\"verifyKey\":\"b14570e2acaa43f2981962dbfd878a34\"}");
//        request.send();

//        GetRequest request = new GetRequest("https://ec-service-dev.byton.cn/api-payment/v1/cms/payments?orderNo=&startTime=&endTime=&tradeType=&status=&page=1&perPage=15");
//        GetRequest request = new GetRequest("https://ec-service-dev.byton.cn/api-payment/v1/cms/allin-balance");
        GetRequest request = new GetRequest("https://ec-service-dev.byton.cn/api-dict/v1/cms/setting/?name=system");
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhZG1pbiI6dHJ1ZSwiZXhwIjoxNTYyNTc0OTUyLCJ1c2VyaWQiOiI1YzZiYmI2NmExODkwZTlmYWY5Mjc5MGUifQ.uHXywUVSQR_jB2wUSmg_Di447DATVqgFMdOIRgH-TkM");
        request.addHeader("Accept", "*/*");
//        request.addUserAgent(UserAgentEnum.CHROME);
//        request.addHeader("Accept-Language", "zh");
//        request.addHttpEntity("123");
        request.send();

    }
}