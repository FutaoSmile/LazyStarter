package com.lazy.httpclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * @author futao
 * Created on 2019/10/16.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestResult {
    private String result;
    private CloseableHttpResponse response;
}
