package com.hrdate.oj.entity.problem;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 题目语言
 * @author: huangrendi
 * @date: 2022-11-18
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Language对象", description="")
@TableName(value = "t_language", autoResultMap = true)
public class Language {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "语言类型")
    @TableField(value = "content_type")
    private String contentType;

    @ApiModelProperty(value = "语言描述")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty(value = "语言名字")
    @TableField(value = "language_name")
    private String name;

    @ApiModelProperty(value = "编译指令")
    @TableField(value = "compile_command")
    private String compileCommand;


    @ApiModelProperty(value = "是否可作为特殊判题的一种语言")
    @TableField(value = "is_spj")
    private Boolean isSpj;

    @ApiModelProperty(value = "该语言属于哪个oj，自身oj用ME")
    @TableField(value = "is_spj")
    private String oj;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}