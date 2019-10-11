package com.lazy.validator.annotations.enums;

import com.lazy.rest.exception.LogicException;

import java.util.Objects;

/**
 * 枚举工具类
 *
 * @author futao
 * Created on 2019/9/30.
 */
public class EnumUtils {

    /**
     * 通过枚举值获取枚举对象
     *
     * @param type         枚举值
     * @param enumClass    枚举类
     * @param errorMessage 没有对应枚举值时的错误提示
     * @param <T>          IEnum
     * @return 枚举对象
     */
    public static <T extends IEnum> IEnum byType(Object type, Class<T> enumClass, String errorMessage) {
        IEnum[] enumConstants = enumClass.getEnumConstants();
        for (IEnum enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.getType(), type)) {
                return enumConstant;
            }
        }
        throw LogicException.le(errorMessage);
    }
}
