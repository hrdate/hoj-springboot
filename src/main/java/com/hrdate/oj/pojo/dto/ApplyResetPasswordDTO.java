package com.hrdate.oj.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
public class ApplyResetPasswordDTO {

    /**
     * 用户输入的验证码
     */
    @NotNull
    private String captcha;

    /**
     * 获取redis中验证码的key
     */
    @NotNull
    private String captchaKey;

    @NotNull
    private String email;
}