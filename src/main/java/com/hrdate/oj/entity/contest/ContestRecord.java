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
 * @description: 比赛用户提交记录
 * @author: huangrendi
 * @date: 2022-11-19
 **/


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ContestRecord对象", description="")
@TableName(value = "t_contest_record", autoResultMap = true)
public class ContestRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "比赛id")
    private Long cid;

    @ApiModelProperty(value = "用户id")
    private String uid;

    @ApiModelProperty(value = "题目id")
    private Long pid;

    @ApiModelProperty(value = "比赛中的题目id")
    private Long cpid;

    @ApiModelProperty(value = "比赛中展示的id")
    private String displayId;

    @ApiModelProperty(value = "提交id，用于可重判")
    private Long submitId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "真实姓名（废弃）")
    private String realname;

    @ApiModelProperty(value = "提交结果，0表示未AC通过不罚时，1表示AC通过，-1为未AC通过算罚时")
    private Integer status;

    @ApiModelProperty(value = "具体提交时间")
    private Date submitTime;

    @ApiModelProperty(value = "提交时间，为提交时间减去比赛时间")
    private Long time;

    @ApiModelProperty(value = "提交的程序运行耗时")
    private Integer useTime;

    @ApiModelProperty(value = "是否为一血AC（废弃）")
    private Boolean firstBlood;

    @ApiModelProperty(value = "AC是否已校验")
    private Boolean checked;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
