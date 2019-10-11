package com.lazy.foundation.annotation;

import java.lang.annotation.*;

/**
 * 不进行Rest封装
 *
 * @author futao
 * Created on 2019-05-22.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RestSkip {
}
