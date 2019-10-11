package com.lazyer.foundation.annotation;


import com.lazyer.foundation.annotation.impl.AssertDataKeyImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 要求必须是某个表的主键
 *
 * @author futao
 * Created on 2019/9/23.
 */
@Constraint(validatedBy = AssertDataKeyImpl.class)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertDataKey {
    /**
     * 表名
     *
     * @return
     */
    String tableName();

    /**
     * 主键列名
     *
     * @return
     */
    String keyColumn() default "id";

    /**
     * 是否允许为空
     *
     * @return
     */
    boolean nullable() default false;

    /**
     * 数据不存在时的提示内容
     *
     * @return
     */
    String message() default "数据不存在，请重新输入";

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
