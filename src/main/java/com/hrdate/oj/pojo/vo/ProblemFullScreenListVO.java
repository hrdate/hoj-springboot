package com.hrdate.oj.pojo.vo;

import lombok.Data;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 */
@Data
public class ProblemFullScreenListVO {

    private Long pid;

    private String problemId;

    private String title;

    private Integer status;

    private Integer score;
}
