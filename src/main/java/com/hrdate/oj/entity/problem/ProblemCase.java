package com.hrdate.oj.entity.problem;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 题目样例
 * @author: huangrendi
 * @date: 2022-11-18
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Case对象", description="题目测试样例")
@TableName(value = "t_problem_case", autoResultMap = true)
public class ProblemCase {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "题目id")
    @TableId(value = "id")
    private Long pid;

    @ApiModelProperty(value = "测试样例的输入")
    @TableField(value = "case_input")
    private String input;

    @ApiModelProperty(value = "测试样例的输出")
    @TableField(value = "case_output")
    private String output;

    @ApiModelProperty(value = "该测试样例的IO得分")
    @TableField(value = "case_score")
    private Integer score;


    @ApiModelProperty(value = "0可用，1不可用")
    @TableField(value = "status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}