package com.hrdate.oj.entity.judge;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: Himit_ZH
 * @Date: 2021/5/18 17:41
 * @Description:
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "RemoteJudgeAccount对象", description = "远程判题服务的账号")
@TableName(value = "t_remote_judge_account", autoResultMap = true)
public class RemoteJudgeAccount {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "远程oj名字")
    private String oj;

    @ApiModelProperty(value = "账号用户名")
    private String username;

    @ApiModelProperty(value = "账号密码")
    private String password;

    @ApiModelProperty(value = "是否可用")
    private Boolean status;

    @ApiModelProperty(value = "废弃")
    private Long version;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}