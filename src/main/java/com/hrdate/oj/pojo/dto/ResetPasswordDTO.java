package com.hrdate.oj.pojo.dto;

import lombok.Data;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */

@Data
public class ResetPasswordDTO {

    private String username;

    private String password;

    private String code;
}