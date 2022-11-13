package com.hrdate.oj.entity;

import com.baomidou.mybatisplus.annotation.*;
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
public class UserInfoModel {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 用户简介
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 创建时间
     */
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
