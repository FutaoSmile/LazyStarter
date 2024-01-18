package com.lazy.rest.utils;

import com.lazy.rest.exception.LogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取国际化资源工具类
 *
 * @author futao
 * Created on 2019-01-15.
 */
public class I18nTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nTools.class);

    private static final String I18N_RESOURCE_NOT_FOUND = "00001_获取国际化资源%s失败";

    /**
     * 获取消息
     *
     * @param code k
     * @return v
     */
    public static String getMessage(String code) {
        try {
            return SpringContextHolder.getContext().getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            LOGGER.error("获取国际化资源{}失败" + e.getMessage(), code, e);
            throw LogicException.le(I18N_RESOURCE_NOT_FOUND, code);
        }
    }
}
