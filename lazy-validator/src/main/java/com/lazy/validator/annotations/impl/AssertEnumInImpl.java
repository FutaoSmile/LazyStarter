package com.lazy.validator.annotations.impl;

import com.lazy.validator.annotations.AssertEnumIn;
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
public class AssertEnumInImpl implements ConstraintValidator<AssertEnumIn, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        AssertEnumIn assertEnumIn = (AssertEnumIn) ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAnnotation();
        if (value == null && assertEnumIn.nullable()) {
            return true;
        } else if (value == null) {
            return false;
        }
        int[] enumValues = assertEnumIn.value();
        for (int enumValue : enumValues) {
            if (value instanceof String && value.equals(String.valueOf(enumValue))) {
                return true;
            }
            if (value instanceof Integer && (int) value == enumValue) {
                return true;
            }
            if (Objects.equals(value, enumValue)) {
                return true;
            }
        }
        return false;
    }
}
