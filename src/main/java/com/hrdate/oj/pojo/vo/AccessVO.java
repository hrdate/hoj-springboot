package com.hrdate.oj.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
public class AccessVO {

    @ApiModelProperty(value = "是否有进入比赛或训练的权限")
    private Boolean access;
}