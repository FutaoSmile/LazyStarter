package com.lazy.validator.annotations;


import com.lazy.validator.annotations.impl.AssertEnumClassImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举值校验
 *
 * @author futao
 * Created on 2019/9/5.
 */
@Constraint(validatedBy = AssertEnumClassImpl.class)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertEnumClass {

    /**
     * 要求的枚举值
     *
     * @return
     */
    Class value();

    /**
     * 不符合枚举值时的提示内容
     *
     * @return
     */
    String message() default "参数错误，请重新输入";

    /**
     * 是否允许为空
     *
     * @return
     */
    boolean nullable() default false;

    /**
     * 作用域
     *
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 变量名称 payload不可变
     * 否则会抛出异常`javax.validation.ConstraintDefinitionException: HV000074`
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}
