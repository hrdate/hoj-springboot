package com.hrdate.oj.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
public class CheckACDTO {

    @NotBlank(message = "比赛记录id不能为空")
    private Long id;

    @NotBlank(message = "比赛id不能为空")
    private Long cid;

    @NotBlank(message = "是否确认不能为空")
    private Boolean checked;
}