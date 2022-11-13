package com.hrdate.oj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 用户登陆表
 * @author: huangrendi
 * @date: 2022-11-09
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "t_user_auth", autoResultMap = true)
public class UserAuthModel {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户信息id
     */
    @TableField(value = "user_info_id")
    private Long userInfoId;

    /**
     * 邮箱号
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 角色（权限）列表
     */
    @TableField(value = "role_list", typeHandler = JacksonTypeHandler.class)
    private List<String> RoleList;

    /**
     * 用户登录ip
     */
    @TableField(value = "ip_address")
    private String ipAddress;

    /**
     * ip来源
     */
    @TableField(value = "ip_source")
    private String ipSource;

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

    /**
     * 最近登录时间
     */
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 是否禁言
     */
    @TableField(value = "is_disable")
    private Boolean isDisable;
}
