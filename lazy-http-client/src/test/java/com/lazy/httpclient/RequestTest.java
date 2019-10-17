package com.lazy.httpclient;

import com.lazy.httpclient.model.RequestResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author futao
 * Created on 2019/10/16.
 */
@Slf4j
public class RequestTest {
    String param = "如何看待CPI超3？央行货币政策司司长孙国峰在发布会上表示，" +
            "当前中国并不存在持续通胀或者通缩的基础，但是也要防止通胀预期扩散，" +
            "防止恶性循环。所以央行也要关注这一预期的变化，要控制预期，" +
            "通过稳健货币政策立场，保持M2和社融规模增长与名义GDP增速基本匹配，" +
            "释放稳健信号。同时存款基准利率保持稳定，贷款通过聚焦LPR（贷款市场报价利率）" +
            "改革稳定预期，能够实现货币政策在降低融资成本与防通胀之间的平衡";

    @Test
    public void test1() {
        work(param);
    }

    public RequestResult work(String param) {
        GetRequest request = new GetRequest("http://127.0.0.1:8888/test/testGetLength");
        request.addParameter("param", param);
        if (request.send().getResponse().getStatusLine().getStatusCode() == 200) {
            param = param + param;
            System.out.println("-----" + param);
            work(param);
        }
        return null;
    }

    @Test
    public void test2(){
        System.out.println("我".getBytes(StandardCharsets.UTF_8).length);
    }
}
