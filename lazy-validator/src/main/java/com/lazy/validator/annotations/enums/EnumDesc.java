package com.lazy.validator.annotations.enums;

import java.lang.annotation.*;

/**
 * @author futao
 * Created on 2019/9/26.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnumDesc {
    String value();
}
