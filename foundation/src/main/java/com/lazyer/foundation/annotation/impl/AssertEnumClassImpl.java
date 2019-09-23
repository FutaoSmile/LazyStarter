package com.lazyer.foundation.annotation.impl;

import com.lazyer.foundation.annotation.AssertEnumClass;
import com.lazyer.foundation.foundation.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 枚举值类校验
 *
 * @author futao
 * Created on 2019/9/23.
 */
@Slf4j
public class AssertEnumClassImpl implements ConstraintValidator<AssertEnumClass, Object> {

    private static final Map<String, Integer[]> CACHE = new HashMap<>();

    @Override
    public void initialize(AssertEnumClass constraintAnnotation) {
        Class enumClass = constraintAnnotation.value();
        Object[] enumConstants = enumClass.getEnumConstants();
        Integer[] cachedValue = CACHE.get(enumClass.getName());
        if (cachedValue == null) {
            //未缓存，需要进行缓存
            Integer[] toSaveValue = new Integer[enumConstants.length];
            CACHE.put(enumClass.getName(), toSaveValue);
            for (int i = 0; i < enumConstants.length; i++) {
                try {
                    Object enumConstant = enumConstants[i];
                    Method method = enumClass.getMethod("getType");
                    Object invoke = method.invoke(enumConstant);
                    toSaveValue[i] = (int) invoke;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw ApplicationException.ae("枚举值%s需要实现IEnum接口", enumClass.getName());
                }
            }
        }
        log.info("手写的枚举值校验初始化完成");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        AssertEnumClass assertEnumClass = (AssertEnumClass) ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAnnotation();
        if (assertEnumClass.nullable() == true && value == null) {
            return true;
        }
        String name = assertEnumClass.value().getName();
        Integer[] enumValues = CACHE.get(name);
        for (Integer enumValue : enumValues) {
            if (Objects.equals(enumValue, value)) {
                return true;
            }
        }
        return false;
    }
}
