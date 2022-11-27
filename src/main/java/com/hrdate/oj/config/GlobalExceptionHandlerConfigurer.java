package com.hrdate.oj.config;


import com.hrdate.oj.enums.ReturnCode;
import com.hrdate.oj.exceptions.ServiceException;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.utils.IReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * 全局处理spring mvc遇到异常，当没有进入到apiController时，
 * 抛出未捕捉的异常的时，会执行到该方法，目前主要用来捕捉上传文件大小超过限制的异常
 *
 * @author huangrendi
 * @since 2022-11-08
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerConfigurer {

    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> onException(ServiceException e) {
        log.error("exception : ", e);
        return createErrorResp(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> onException(Exception e, HttpServletRequest request) {
        log.error("exception : ", e);
        String uri = request.getRequestURI();
        if (e instanceof HttpMediaTypeNotSupportedException) {
            return createErrorResp(415, e.getMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return createErrorResp(405, e.getMessage());
        } else if (e instanceof SQLException || e instanceof DataAccessException) {
            return createErrorResp(ReturnCode.DB_ERROR);
        } else if (e instanceof ServiceException) {
            ServiceException ex = (ServiceException) e;
            return createErrorResp(ex.getCode(), e.getMessage());
        }else if (e instanceof MethodArgumentNotValidException) {
            log.error("uri:{},message:{}", uri, e.getMessage(), e);
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = ex.getBindingResult();
            StringBuilder errorMessage = new StringBuilder("参数校验错误: ");
            if (!CollectionUtils.isEmpty(bindingResult.getFieldErrors())) {
                String message = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                errorMessage.append(message);
            }
            return createErrorResp(ReturnCode.PARAM_ERROR, errorMessage.toString());
        } else if (e instanceof BindException) {
            log.error("uri:{},message:{}", uri, e.getMessage(), e);
            BindException ex = (BindException) e;
            BindingResult bindingResult = ex.getBindingResult();
            StringBuilder errorMessage = new StringBuilder("参数校验错误: ");
            if (!CollectionUtils.isEmpty(bindingResult.getFieldErrors())) {
                String message = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                errorMessage.append(message);
            }
            return createErrorResp(ReturnCode.PARAM_ERROR, errorMessage.toString());
        } else if (e instanceof TypeMismatchException
                || e instanceof ServletRequestBindingException) {
            log.error("uri:{},message:{}", uri, e.getMessage(), e);
            return createErrorResp(ReturnCode.PARAM_ERROR, e.getMessage());
        } else {
            log.error("uri:{},message:{}", uri, e.getMessage(), e);
            return createErrorResp(ReturnCode.SERVER_ERROR);
        }
    }

    private CommonResult<?> createErrorResp(IReturnCode returnCode) {
        return CommonResult.failedResult(returnCode.getMsg(), returnCode.getCode());
    }

    private CommonResult<?> createErrorResp(int returnCode, String message) {
        return CommonResult.failedResult(message, returnCode);
    }

    private CommonResult<?> createErrorResp(IReturnCode returnCode, String message) {
        return CommonResult.failedResult(StringUtils.isNotBlank(message) ? message : returnCode.getMsg(), returnCode.getCode());
    }

}
