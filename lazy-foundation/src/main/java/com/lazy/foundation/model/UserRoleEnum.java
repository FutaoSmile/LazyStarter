package com.lazy.foundation.model;


import com.lazy.constant.ErrorMessage;
import com.lazy.rest.exception.LogicException;
import com.lazy.validator.annotations.enums.IEnum;

/**
 * 角色
 *
 * @author futao
 * Created on 2018/9/19-14:41.
 */
public enum UserRoleEnum implements IEnum<Integer> {
    /**
     * 普通登录用户
     */
    NORMAL(1, "普通用户"),
    /**
     * 管理员用户
     */
    ADMIN(2, "管理员");

    private int type;
    private String description;

    UserRoleEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public Integer getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 通过type来查询枚举值
     *
     * @param roleType
     * @return
     */
    public static UserRoleEnum value(int roleType) {
        if (roleType == UserRoleEnum.NORMAL.getType()) {
            return UserRoleEnum.NORMAL;
        } else if (roleType == UserRoleEnum.ADMIN.getType()) {
            return UserRoleEnum.ADMIN;
        }
        throw LogicException.le(ErrorMessage.LogicErrorMessage.ROLE_NOT_EXIST);
    }

}
