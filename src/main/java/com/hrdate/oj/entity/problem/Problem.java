package com.hrdate.oj.entity.problem;

import com.baomidou.mybatisplus.annotation.*;
import com.hrdate.oj.enums.ProblemTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 题目
 * @author: huangrendi
 * @date: 2022-11-18
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Problem对象", description="")
@TableName(value = "t_problem", autoResultMap = true)
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "题目的自定义ID 例如（HOJ-1000）")
    @TableId(value = "problem_id")
    private String problemId;

    @ApiModelProperty(value = "题目")
    @TableId(value = "problem_title")
    private String title;

    @ApiModelProperty(value = "作者")
    @TableId(value = "problem_author")
    private String author;

    @ApiModelProperty(value = "0为ACM,1为OI")
    @TableId(value = "problem_type")
    private ProblemTypeEnum type;

    @ApiModelProperty(value = "default,spj,interactive")
    @TableId(value = "problem_judge_mode")
    private String judgeMode;

    @ApiModelProperty(value = "default,subtask_lowest,subtask_average")
    @TableId(value = "problem_judge_case_mode")
    private String judgeCaseMode;

    @ApiModelProperty(value = "单位ms")
    @TableId(value = "problem_time_limit")
    private Integer timeLimit;

    @ApiModelProperty(value = "单位mb")
    @TableId(value = "problem_memory_limit")
    private Integer memoryLimit;

    @ApiModelProperty(value = "单位mb")
    @TableId(value = "problem_stack_limit")
    private Integer stackLimit;

    @ApiModelProperty(value = "描述")
    @TableId(value = "problem_description")
    private String description;

    @ApiModelProperty(value = "输入描述")
    @TableId(value = "problem_input")
    private String input;

    @ApiModelProperty(value = "输出描述")
    @TableId(value = "problem_output")
    private String output;

    @ApiModelProperty(value = "题面样例")
    @TableId(value = "problem_examples")
    private String examples;

    @ApiModelProperty(value = "是否为vj判题")
    @TableId(value = "problem_is_remote")
    private Boolean isRemote;

    @ApiModelProperty(value = "题目来源（vj判题时例如HDU-1000的链接）")
    @TableId(value = "problem_source")
    private String source;

    @ApiModelProperty(value = "题目难度")
    @TableId(value = "problem_difficulty")
    private Integer difficulty;

    @ApiModelProperty(value = "备注,提醒")
    @TableId(value = "problem_hint")
    private String hint;

    @ApiModelProperty(value = "默认为1公开，2为私有，3为比赛中")
    @TableId(value = "problem_auth")
    private Integer auth;

    @ApiModelProperty(value = "当该题目为oi题目时的分数")
    @TableId(value = "problem_us_score")
    private Integer ioScore;

    @ApiModelProperty(value = "该题目对应的相关提交代码，用户是否可用分享")
    @TableId(value = "problem_code_share")
    private Boolean codeShare;

    @ApiModelProperty(value = "特判程序或交互程序的代码")
    @TableField(value="spj_code",updateStrategy = FieldStrategy.IGNORED)
    @TableId(value = "problem_spj_code")
    private String spjCode;

    @ApiModelProperty(value = "特判程序或交互程序的语言")
    @TableField(value="spj_language",updateStrategy = FieldStrategy.IGNORED)
    @TableId(value = "problem_spj_language")
    private String spjLanguage;

    @ApiModelProperty(value = "特判程序或交互程序的额外文件 json key:name value:content")
    @TableField(value="user_extra_file",updateStrategy = FieldStrategy.IGNORED)
    private String userExtraFile;

    @ApiModelProperty(value = "特判程序或交互程序的额外文件 json key:name value:content")
    @TableField(value="judge_extra_file",updateStrategy = FieldStrategy.IGNORED)
    private String judgeExtraFile;

    @ApiModelProperty(value = "是否默认去除用户代码的每行末尾空白符")
    @TableField(value = "problem_is_remove_end_blank")
    private Boolean isRemoveEndBlank;

    @ApiModelProperty(value = "是否默认开启该题目的测试样例结果查看")
    @TableField(value = "problem_is_remove_end_blank")
    private Boolean openCaseResult;

    @ApiModelProperty(value = "题目测试数据是否是上传的")
    @TableField(value = "proble_is_upload_case")
    private Boolean isUploadCase;

    @ApiModelProperty(value = "题目测试数据的版本号")
    @TableField(value = "problem_case_version")
    private String caseVersion;

    @ApiModelProperty(value = "修改题目的管理员用户名")
    @TableField(value = "problem_modified_user")
    private String modifiedUser;

    @ApiModelProperty(value = "申请公开的进度：null为未申请，1为申请中，2为申请通过，3为申请拒绝")
    @TableField(value = "problem_apply_public_progress")
    private Integer applyPublicProgress;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
