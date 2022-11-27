package com.hrdate.oj.entity.msg;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-18
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="t_admin_sys_notice", description="")
public class AdminSysNotice {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "通知标题")
    private String title;

    @ApiModelProperty(value = "通知内容")
    private String content;

    @ApiModelProperty(value = "发给哪些用户类型,例如全部用户All，指定单个用户Single，管理员Admin")
    private String type;

    @ApiModelProperty(value = "是否已被拉取过，如果已经拉取过，就无需再次拉取")
    private Boolean state;

    @ApiModelProperty(value = "接受通知的用户的id，如果type为single，那么recipient 为该用户的id;否则recipient为null")
    private String recipientId;

    @ApiModelProperty(value = "发布通知的管理员id")
    private String adminId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}