package com.lazy.rest.rest;

import com.lazy.rest.exception.ApplicationException;
import com.lazy.rest.exception.LogicException;
import com.lazy.rest.utils.I18nTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * 异常处理
 *
 * @author futao
 * Created on 2018/11/6.
 * //@ControllerAdvice(basePackages = "com.futao.springbootdemo.controller")
 */
@RestControllerAdvice
public class ExceptionWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionWrapper.class);

    /**
     * 全局异常处理
     *
     * @param e        异常
     * @param request  请求
     * @param response 响应
     * @return 处理后的异常的数据结构
     */
    @ExceptionHandler(value = {Exception.class, ApplicationException.class})
    public Object exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
        printExceptionLog(e);
        //系统级异常，错误码固定为-1，提示语固定为系统繁忙，请稍后再试
        RestResult result = new RestResult<>(RestResult.SYSTEM_ERROR_CODE, e.getMessage(), I18nTools.getMessage("system.exception"));
        //对系统级异常进行日志记录
        LOGGER.error("系统异常:" + e.getMessage(), e);
        return result;
    }

    /**
     * 如果是业务逻辑异常，返回具体的错误码与提示信息
     *
     * @param e        异常
     * @param request  请求
     * @param response 响应
     * @return 处理后的异常的数据结构
     */
    @ExceptionHandler(LogicException.class)
    public Object logicExceptionHandler(LogicException e, HttpServletRequest request, HttpServletResponse response) {
        RestResult result = new RestResult<>(RestResult.SYSTEM_ERROR_CODE, e.getMessage(), I18nTools.getMessage("system.exception"));
        result.setCode(e.getCode());
        result.setErrorMessage(e.getErrorMsg());
        return result;
    }


    private static final String SPLITER = "_";

    /**
     * Validator验证框架抛出的业务逻辑异常
     *
     * @param e        异常
     * @param request  请求
     * @param response 响应
     * @return 处理后的异常的数据结构
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Object constraintViolationException(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        RestResult result = new RestResult<>(RestResult.SYSTEM_ERROR_CODE, e.getMessage(), I18nTools.getMessage("system.exception"));
        if (message.contains(SPLITER)) {
            result.setCode(message.substring(0, 5));
            result.setErrorMessage(message.substring(6));
        } else {
            result.setCode(RestResult.NOT_RE_WRITE_ERROR_MESSAGE);
            result.setErrorMessage(message);
        }
        return result;
    }


    /**
     * 请求超时异常
     *
     * @param e 异常信息
     * @return
     */
    @ExceptionHandler(TransactionTimedOutException.class)
    public Object transactionTimedOutException(TransactionTimedOutException e) {
        printExceptionLog(e);
        return new RestResult<>(RestResult.SYSTEM_ERROR_CODE, e.getMessage(), I18nTools.getMessage("system.transaction.timeout"));
    }

    /**
     * 处理400,404,405,500等问题
     * 详情{@link org.springframework.web.servlet.DispatcherServlet#noHandlerFound}
     *
     * @param e        异常信息
     * @param response 响应
     * @return
     */
    @ExceptionHandler(ServletException.class)
    public Object httpException(ServletException e, HttpServletResponse response) {
        printExceptionLog(e);
        RestResult result = new RestResult<>(RestResult.SYSTEM_ERROR_CODE, e.getMessage(), I18nTools.getMessage("system.exception"));

        if (e instanceof HttpRequestMethodNotSupportedException) {
            result.setCode(String.valueOf(HttpServletResponse.SC_METHOD_NOT_ALLOWED));
            result.setErrorMessage(String.format(I18nTools.getMessage("system.method.not.allow"), ((HttpRequestMethodNotSupportedException) e).getMethod()));
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else if (e instanceof MissingServletRequestParameterException) {
            result.setCode(String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
            result.setErrorMessage(String.format(I18nTools.getMessage("system.bad.request"), ((MissingServletRequestParameterException) e).getParameterName()));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (e instanceof NoHandlerFoundException) {
            result.setCode(String.valueOf(HttpServletResponse.SC_NOT_FOUND));
            result.setErrorMessage(I18nTools.getMessage("system.not.found"));
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            result.setCode(String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            result.setErrorMessage(I18nTools.getMessage("system.internal.server.error"));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 记录异常日志
     *
     * @param e 异常
     */
    private static void printExceptionLog(Exception e) {
        LOGGER.error(e.getMessage(), e);
    }

}
