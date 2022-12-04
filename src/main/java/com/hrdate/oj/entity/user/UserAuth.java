package com.hrdate.oj.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
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
public class UserAuth {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField(value = "user_id")
    private String uid;


    @ApiModelProperty(value = "邮箱号")
    @TableField(value = "email")
    private String email;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty(value = "角色（权限）列表")
    @TableField(value = "role_list", typeHandler = JacksonTypeHandler.class)
    private List<String> RoleList;

    @ApiModelProperty(value = "用户登录ip")
    @TableField(value = "ip_address")
    private String ipAddress;

    @ApiModelProperty(value = "ip来源")
    @TableField(value = "ip_source")
    private String ipSource;


    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "最近登录时间")
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "是否禁言")
    @TableField(value = "is_disable")
    private Boolean isDisable;
}
