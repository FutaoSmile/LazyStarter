package com.lazyer.foundation.foundation.exception;

import com.lazyer.foundation.utils.I18nTools;

/**
 * 逻辑异常
 *
 * @author futao
 * Created on 2018/9/20-15:22.
 */
public class LogicException extends ApplicationException {
    /**
     * 异常信息
     */
    private String errorMsg;
    /**
     * 错误码
     */
    private String code;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private LogicException(String errorMsg) {
        super(errorMsg);
        this.code = errorMsg.substring(0, 5);
        this.errorMsg = errorMsg.substring(6);
    }

    private LogicException(String code, String errorMsg) {
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    /**
     * 抛出逻辑异常
     *
     * @param errorMsg 异常信息
     * @return
     */
    public static LogicException le(String errorMsg) {
        return new LogicException(errorMsg);
    }

    /**
     * 抛出国际化异常提示
     *
     * @param key 国际化配置异常代码
     * @return
     */
    public static LogicException leI18(String key) {
        return new LogicException(I18nTools.getMessage(key));
    }

    /**
     * 抛出逻辑异常，带有占位符
     *
     * @param errorMsg 错误详情
     * @param args     替换占位符内的内容
     * @return
     */
    public static LogicException le(String errorMsg, Object[] args) {
        return new LogicException(String.format(errorMsg, args));
    }

    /**
     * 抛出逻辑异常
     *
     * @param code     错误码
     * @param errorMsg 错误详情
     * @return
     */
    public static LogicException le(String code, String errorMsg) {
        return new LogicException(code, errorMsg);
    }
}
