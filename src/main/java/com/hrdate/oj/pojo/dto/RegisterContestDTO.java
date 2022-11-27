package com.hrdate.oj.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
public class RegisterContestDTO {

    @NotBlank(message = "cid不能为空")
    private Long cid;

    @NotBlank(message = "password不能为空")
    private String password;
}