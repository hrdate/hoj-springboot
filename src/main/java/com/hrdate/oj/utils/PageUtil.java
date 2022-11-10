package com.hrdate.oj.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtil {

    private PageUtil() {
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     */
    public static <T> Map<String, Object> toPage(T records, long total) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", records);
        map.put("total", total);
        return map;
    }

    /**
     * List<T> 分页
     */
    public static <T> Map<String, Object> toPage(int page, int size, List<T> list) {
        if (list == null || list.isEmpty()) {
            return toPage(null, 0);
        } else {
            int start = (page - 1) * size;
            int end = start + size;
            if (start > list.size()) {
                return toPage(null, 0);
            } else if (end >= list.size()) {
                return toPage(list.subList(start, list.size()), list.size());
            } else {
                return toPage(list.subList(start, end), list.size());
            }
        }
    }
}