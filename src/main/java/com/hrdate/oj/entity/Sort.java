package com.hrdate.oj.entity;


import lombok.Data;

@Data
public class Sort {

    /**
     * 排序的字段
     */
    private String key;

    /**
     * 排序的类型: ASE或者是DESC
     */
    private String type;
}