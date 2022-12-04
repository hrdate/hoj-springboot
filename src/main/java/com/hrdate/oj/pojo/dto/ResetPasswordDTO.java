package com.hrdate.oj.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */

@Data
public class ResetPasswordDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String code;
}