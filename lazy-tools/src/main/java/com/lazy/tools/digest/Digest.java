package com.lazy.tools.digest;

import java.lang.annotation.*;

/**
 * 需要对被标记的Url的请求参数进行数字摘要验证
 *
 * @author futao
 * Created on 2019/10/22.
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Digest {
}
