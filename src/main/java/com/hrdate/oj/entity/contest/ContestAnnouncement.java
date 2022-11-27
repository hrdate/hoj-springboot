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
 * @description: 比赛内公告表
 * @author: huangrendi
 * @date: 2022-11-19
 **/


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ContestAnnouncement对象", description="")
@TableName(value = "t_contest_announcement", autoResultMap = true)
public class ContestAnnouncement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公告id")
    private Long aid;

    @ApiModelProperty(value = "比赛id")
    private Long cid;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
