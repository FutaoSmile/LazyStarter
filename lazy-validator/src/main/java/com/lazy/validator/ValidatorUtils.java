package com.lazy.validator;

import com.lazy.rest.exception.LogicException;
import com.lazy.rest.utils.SpringTools;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

/**
 * 校验工具类
 *
 * @author futao
 * Created on 2019/11/1.
 */
public class ValidatorUtils {

    /**
     * 主动校验对象
     * JSR303校验对象
     *
     * @param obj
     */
    public static void checkObj(Object obj) {
        Errors errors = new DirectFieldBindingResult(obj, "obj");
        SpringTools.getBean(SmartValidator.class).validate(obj, errors);
        if (errors.getAllErrors().size() > 0) {
            throw LogicException.le(errors.getAllErrors().get(0).getDefaultMessage());
        }
    }
}
