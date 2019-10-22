package com.lazy.tools.digest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 摘要认证与签名认证
 *
 * @author futao
 * Created on 2019/10/22.
 */
@Component
@ConfigurationProperties(prefix = "lazy.tools.request-message-digest")
public class RequestMessageDigest {

    /**
     * 摘要名称
     */
    private String mdName;

    public static boolean verify() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
//        if (MapUtils.isNotEmpty(parameterMap)) {
//            TreeMap<String, Object> treeMap = new TreeMap<>();
//            treeMap.putAll(parameterMap);
//        }
        return false;
    }

    public String getMdName() {
        return mdName;
    }

    public void setMdName(String mdName) {
        this.mdName = mdName;
    }
}
