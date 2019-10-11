package com.lazy.validator.annotations.enums;

/**
 * 枚举值都需要实现的接口，用来约束枚举值，以及给出提示
 *
 * @author futao
 * Created on 2019/9/5.
 */
public interface IEnum<T> {

    /**
     * 类型
     *
     * @return
     */
    T getType();

    /**
     * 描述
     *
     * @return
     */
    String getDescription();
}
