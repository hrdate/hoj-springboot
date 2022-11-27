package com.hrdate.oj.entity.contest;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 笔试题目
 * @author: huangrendi
 * @date: 2022-11-19
 **/


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ContestProblem对象", description="")
@TableName(value = "t_contest_problem", autoResultMap = true)
public class ContestProblem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "该题目在比赛中的顺序id")
    private String displayId;

    @ApiModelProperty(value = "比赛id")
    private Long cid;

    @ApiModelProperty(value = "题目id")
    private Long pid;

    @ApiModelProperty(value = "该题目在比赛中的标题，默认为原名字")
    private String displayTitle;

    @ApiModelProperty(value = "气球的颜色")
    private String color;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
