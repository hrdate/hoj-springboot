package com.hrdate.oj.enums;

import lombok.Getter;

/**
 * @author <a href="mailto:chenyuexin@joyy.sg">chenyuexin</a>
 * @date 2021/9/16 21:12
 */
@Getter
public enum OrderByEnum {

    /**
     * 升序
     */
    ASC("asc", "升序"),
    /**
     * 降序
     */
    DESC("desc", "降序");

    /**
     * week
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String msg;

    OrderByEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据枚举编码获取枚举项
     *
     * @param code 枚举编码
     * @return 枚举项
     */
    public static OrderByEnum getByCode(String code) {
        OrderByEnum[] values = OrderByEnum.values();
        for (OrderByEnum item : values) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
