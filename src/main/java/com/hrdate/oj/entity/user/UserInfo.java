package com.hrdate.oj.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * @description: 用户信息表
 * @author: huangrendi
 * @date: 2022-11-08
 **/

@Data
@FieldNameConstants
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "t_user_info", autoResultMap = true)
public class UserInfo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField(value = "user_id")
    private String uid;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    @TableField(value = "nickname")
    private String nickname;

    @ApiModelProperty(value = "性别")
    @TableField(value = "gender")
    private String gender;

    @TableField(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "个性介绍")
    @TableField(value = "signature", updateStrategy = FieldStrategy.IGNORED)
    private String signature;

    @ApiModelProperty(value = "学号")
    @TableField(value = "number", updateStrategy = FieldStrategy.IGNORED)
    private String number;

    @ApiModelProperty(value = "学校")
    @TableField(value = "school", updateStrategy = FieldStrategy.IGNORED)
    private String school;

    @ApiModelProperty(value = "专业")
    @TableField(value = "course",updateStrategy = FieldStrategy.IGNORED)
    private String course;

    @ApiModelProperty(value = "头衔、称号")
    @TableField(value = "title_name",updateStrategy = FieldStrategy.IGNORED)
    private String titleName;

    @ApiModelProperty(value = "头衔、称号的颜色")
    @TableField(value = "title_color",updateStrategy = FieldStrategy.IGNORED)
    private String titleColor;


    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
