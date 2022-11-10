package com.hrdate.oj.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.hrdate.oj.entity.Sort;
import com.hrdate.oj.enums.OrderByEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class SortUtil {

    /**
     * 排序工具类
     */
    public static List<OrderItem> sort2OrderItem(List<Sort> sort) {
        if (CollectionUtils.isEmpty(sort)) {
            return Lists.newArrayList();
        }

        return sort.stream().map(s -> {
            String key = s.getKey();
            String type = s.getType();
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key));
            orderItem.setAsc(OrderByEnum.ASC.getCode().equals(type));

            return orderItem;
        }).collect(Collectors.toList());
    }

    /**
     * 集合排序
     *
     * @param coll 集合
     * @param sort {@link Sort} 对象
     */
    public static void orderList(List<?> coll, Sort sort) {
        if (CollectionUtils.isEmpty(coll)) {
            return;
        }
        coll.sort((o1, o2) -> {
            Class<?> clazz = coll.get(0).getClass();
            int order = 0;
            try {
                Field field = clazz.getDeclaredField(sort.getKey());
                field.setAccessible(true);
                if (sort.getType().equalsIgnoreCase(OrderByEnum.ASC.getCode())) {
                    if (field.get(o1) == null && field.get(o2) == null) {
                        return 0;
                    } else if (field.get(o1) == null) {
                        return -1;
                    } else if (field.get(o2) == null) {
                        return 1;
                    } else {
                        order = field.get(o1).toString().compareTo(field.get(o2).toString());
                    }
                } else if (sort.getType().equalsIgnoreCase(OrderByEnum.DESC.getCode())) {
                    if (field.get(o1) == null && field.get(o2) == null) {
                        return 0;
                    } else if (field.get(o1) == null) {
                        return 1;
                    } else if (field.get(o2) == null) {
                        return -1;
                    } else {
                        order = field.get(o2).toString().compareTo(field.get(o1).toString());
                    }
                }
                return order;
            } catch (Exception e) {
                log.error("SortUtil#orderList Order failed ", e);
                return order;
            }
        });
    }

    /**
     * 集合排序
     *
     * @param coll     集合
     * @param sortList {@link Sort} 对象集合
     */
    public static void orderList(List<?> coll, List<Sort> sortList) {
        sortList.forEach(item -> orderList(coll, item));
    }

}
