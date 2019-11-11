package com.jeebud.core.web;

import com.jeebud.common.constant.ErrCodeEnum;
import com.jeebud.common.exception.JeebudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionHandle {

    /**
     * jpa查询ID查询数据为空的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoSuchElementException.class)
    public RestEntity handleNoSuchElementException(NoSuchElementException e) {
        log.error("数据为空", e);
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message(e.getMessage());
    }


    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(JeebudException.class)
    public Object handleBusinessException(HttpServletRequest request, JeebudException e) {
        log.error("业务异常", e);
        if (isAjax(request)) {
            return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message(e.getMessage());
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/err/500");
            return modelAndView;
        }
    }

    /**
     * 校验参数异常统一处理
     *
     * @param e
     * @return
     * @Validated @RequestBody 没有RequestBody时候抛出此异常
     * https://github.com/spring-projects/spring-framework/issues/14790
     */
    @ExceptionHandler(BindException.class)
    public RestEntity handleBindException(BindException e) {
        log.error("参数校验异常", e);
        BindingResult bindingResult = e.getBindingResult();
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message(bindingResult.getFieldError().getDefaultMessage());
    }

    /**
     * 校验参数异常统一处理
     *
     * @param e
     * @return
     * @Validated @RequestBody 一起的时候抛出此异常
     * https://github.com/spring-projects/spring-framework/issues/14790
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestEntity handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            String field = error.getField();
            Object value = error.getRejectedValue();
            String msg = error.getDefaultMessage();
            String message = String.format("错误字段：%s，错误值：%s，原因：%s；", field, value, msg);
            stringBuilder.append(message).append("\r\n");
        }
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message(stringBuilder.toString());
    }

    /**
     * 参数不合法异常统一处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public RestEntity handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("断言参数异常", e);
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message(e.getMessage());
    }

    /**
     * JPA删除数据不存在异常统一处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public RestEntity handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("JPA删除数据不存在", e);
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message("操作的数据不存在");
    }

    /**
     * 不支持的请求方式
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持的请求方式", e);
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message("不支持" + e.getMethod() + "请求方式");
    }


    /**
     * 参数类型错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RestEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型错误", e);
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message("参数类型错误");
    }

    /**
     * 参数缺失错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RestEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("参数错误", e);
        return RestEntity.failed().code(ErrCodeEnum.BIZ_ERROR.getCode()).message("参数错误：" + e.getMessage());
    }

    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Object handleUnauthorizedException(HttpServletRequest request, HandlerMethod handlerMethod, Exception e) {
        log.error("无权限", e);
        if (isAjax(request)) {
            return RestEntity.failed().code(ErrCodeEnum.FORBIDDEN.getCode()).message(ErrCodeEnum.FORBIDDEN.getMsg());
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/err/403");
            return modelAndView;
        }
    }

    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, HandlerMethod handlerMethod, Exception e) {
        log.error("内部错误", e);
        if (isAjax(request)) {
            return RestEntity.failed().code(ErrCodeEnum.ERROR.getCode()).message(ErrCodeEnum.ERROR.getMsg());
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/err/500");
            return modelAndView;
        }
    }

    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }
}
