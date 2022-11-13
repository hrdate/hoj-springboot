package com.hrdate.oj.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author yezhiqiu
 * @date 2021/08/01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "t_operation_log", autoResultMap = true)
public class OperationLogModel {

    /**
     * 日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作模块
     */
    @TableField(value = "opt_module")
    private String optModule;

    /**
     * 操作路径
     */
    @TableField(value = "opt_url")
    private String optUrl;

    /**
     * 操作类型
     */
    @TableField(value = "opt_type")
    private String optType;

    /**
     * 操作方法
     */
    @TableField(value = "opt_method")
    private String optMethod;

    /**
     * 操作描述
     */
    @TableField(value = "opt_des")
    private String optDesc;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    private String requestParam;

    /**
     * 返回数据
     */
    @TableField(value = "response_data")
    private String responseData;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

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
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time" ,fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
