package com.lazyer.foundation.foundation.rest;

import com.lazyer.foundation.annotation.IgnoreRestAdvice;
import com.lazyer.foundation.model.RestResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回Rest风格的数据
 *
 * @author futao
 * Created on 2018/9/22-20:24.
 */
@ControllerAdvice
public class RestResultWrapper implements ResponseBodyAdvice<Object> {

    /**
     * 可指定针对某些返回值的类型才进行rest风格的封装
     *
     * @param returnType    返回值类型
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getDeclaringClass().isAnnotationPresent(IgnoreRestAdvice.class) && !returnType.getMethod().isAnnotationPresent(IgnoreRestAdvice.class);
    }

    /**
     * 封装正常返回的数据
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (MediaType.IMAGE_JPEG.getType().equalsIgnoreCase(selectedContentType.getType())) {
            return body;
        }
        return new RestResult<>(true, RestResult.SUCCESS_CODE, body, null);
    }
}
