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
 * @description: 题目-标签关系表
 * @author: huangrendi
 * @date: 2022-11-18
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ProblemTag对象", description="")
@TableName(value = "t_problem_tag", autoResultMap = true)
public class ProblemTag {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "题目id")
    @TableField(value = "problem_id" , fill = FieldFill.INSERT)
    private Long pid;

    @ApiModelProperty(value = "标签id")
    @TableField(value = "tag_id" , fill = FieldFill.INSERT)
    private Long tid;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}