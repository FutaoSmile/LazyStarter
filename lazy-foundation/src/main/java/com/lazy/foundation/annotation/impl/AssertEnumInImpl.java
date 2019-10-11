package com.lazy.foundation.annotation.impl;

import com.lazy.foundation.annotation.AssertEnumIn;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 手写的枚举值校验
 *
 * @author futao
 * Created on 2019/9/23.
 */
@Slf4j
public class AssertEnumInImpl implements ConstraintValidator<AssertEnumIn, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        AssertEnumIn assertEnumIn = (AssertEnumIn) ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAnnotation();
        if (assertEnumIn.nullable() == true && value == null) {
            return true;
        }
        int[] enumValues = assertEnumIn.value();
        for (int enumValue : enumValues) {
            if (Objects.equals(value, enumValue)) {
                return true;
            }
        }
        return false;
    }
}
