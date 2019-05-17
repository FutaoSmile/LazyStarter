package com.lazyer.foundation.annotation;

import java.lang.annotation.*;

/**
 * 忽略RestResultAdvice
 *
 * @author futao
 * Created on 2019-05-06.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IgnoreRestAdvice {
}
