package com.hrdate.oj.pojo.dto;

import lombok.Data;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 */
@Data
public class LastAcceptedCodeVO {

    private Long submitId;

    private String code;

    private String language;
}
