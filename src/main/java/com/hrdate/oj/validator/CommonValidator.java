package com.hrdate.oj.validator;

import cn.hutool.core.util.StrUtil;
import com.hrdate.oj.exceptions.ServiceException;
import org.springframework.stereotype.Component;

/**
 * @Author Himit_ZH
 * @Date 2022/10/22
 */
public class CommonValidator {

    public static void validateContent(String content, String item) throws ServiceException {
        if (StrUtil.isBlank(content)) {
            throw new ServiceException(item + "的内容不能为空！");
        }
        if (content.length() > 65535) {
            throw new ServiceException(item + "的内容长度超过限制，请重新编辑！");
        }
    }

    public static void validateContent(String content, String item, int length) throws SecurityException {
        if (StrUtil.isBlank(content)) {
            throw new ServiceException(item + "的内容不能为空！");
        }
        if (content.length() > length) {
            throw new ServiceException(item + "的内容长度超过限制，请重新编辑！");
        }
    }

    public static void validateContentLength(String content, String item, int length) throws ServiceException {
        if (content != null && content.length() > length) {
            throw new ServiceException(item + "的内容长度超过限制，请重新编辑！");
        }
    }



    public void validateNotEmpty(Object value, String item) throws ServiceException {
        if (value == null) {
            throw new ServiceException(item + "不能为空");
        }
        if (value instanceof String){
            if (StrUtil.isBlank((String)value)){
                throw new ServiceException(item + "不能为空");
            }
        }
    }
}
