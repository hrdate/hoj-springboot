package com.hrdate.oj.utils;


import com.google.common.collect.Lists;
import com.hrdate.oj.exceptions.ServiceException;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author huangrendi
 * @date 2022-11-08
 */
public class BeanCopyUtil {

    private BeanCopyUtil() {

    }

    /**
     * copy bean
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>    目标对象的类型
     * @return 目标对象
     */
    public static <T> T copy(Object source, Class<T> target) {
        if (Objects.nonNull(source)) {
            try {
                T instance = target.newInstance();
                BeanUtils.copyProperties(source, instance);
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new ServiceException("转换参数出现异常");
    }

    /**
     * 复制整个列表
     *
     * @param source  源对象
     * @param toClass List的实体对象
     * @param <S>     内部实体
     * @param <T>     目标对象的类型
     * @return List<T>
     */
    public static <S, T> List<T> copyList(List<S> source, Class<T> toClass) {
        List<T> content = Lists.newArrayList();
        source.forEach(entity -> content.add(copy(entity, toClass)));
        return content;
    }
}