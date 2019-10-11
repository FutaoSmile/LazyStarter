package com.lazyer.foundation.annotation;

import com.lazyer.foundation.model.UserRoleEnum;

import java.lang.annotation.*;

/**
 * 用户权限控制
 *
 * @author futao
 * Created on 2018-12-13.
 */
@Target({
        ElementType.TYPE,
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@LoginUser
public @interface Role {
    /**
     * 要求的用户角色
     *
     * @return
     */
    UserRoleEnum[] value() default UserRoleEnum.NORMAL;
}
