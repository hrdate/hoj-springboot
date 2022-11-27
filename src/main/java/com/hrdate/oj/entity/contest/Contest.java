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
 * @description: 比赛表
 * @author: huangrendi
 * @date: 2022-11-19
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Contest对象", description = "")
@TableName(value = "t_contest", autoResultMap = true)
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "比赛id")
    private Long id;

    @ApiModelProperty(value = "比赛创建者id")
    private String uid;

    @ApiModelProperty(value = "比赛创建者的用户名")
    private String author;

    @ApiModelProperty(value = "比赛标题")
    private String title;

    @ApiModelProperty(value = "0为acm赛制，1为比分赛制")
    private Integer type;

    @ApiModelProperty(value = "比赛说明")
    private String description;

    @ApiModelProperty(value = "比赛来源，原创为0，克隆赛为比赛id")
    private Integer source;

    @ApiModelProperty(value = "0为公开赛，1为私有赛（访问有密码），2为保护赛（提交有密码）")
    private Integer auth;

    @ApiModelProperty(value = "比赛密码")
    private String pwd;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "比赛时长（s）")
    private Long duration;

    @ApiModelProperty(value = "是否开启封榜")
    private Boolean sealRank;

    @ApiModelProperty(value = "封榜起始时间，一直到比赛结束，不刷新榜单")
    private Date sealRankTime;

    @ApiModelProperty(value = "比赛结束是否自动解除封榜,自动转换成真实榜单")
    private Boolean autoRealRank;

    @ApiModelProperty(value = "-1为未开始，0为进行中，1为已结束")
    private Integer status;

    @ApiModelProperty(value = "是否可见")
    private Boolean visible;

    @ApiModelProperty(value = "是否打开打印功能")
    private Boolean openPrint;

    @ApiModelProperty(value = "是否打开账号限制")
    private Boolean openAccountLimit;

    @ApiModelProperty(value = "账号限制规则 <prefix>**</prefix><suffix>**</suffix><start>**</start><end>**</end><extra>**</extra>")
    private String accountLimitRule;

    @ApiModelProperty(value = "排行榜显示（username、nickname、realname）")
    private String rankShowName;

    @ApiModelProperty(value = "打星用户列表 {\"star_account\":['a','b']}")
    private String starAccount;

    @ApiModelProperty(value = "是否开放比赛榜单")
    private Boolean openRank;

    @ApiModelProperty(value = "oi排行榜得分方式，Recent、Highest（最近一次提交、最高得分提交）")
    private String oiRankScoreType;

    @ApiModelProperty(value = "是否为团队内比赛")
    private Boolean isGroup;

    @ApiModelProperty(value = "团队ID")
    private Long gid;

    @ApiModelProperty(value = "奖项类型：0(不设置),1(设置占比),2(设置人数)")
    private Integer awardType;

    @ApiModelProperty(value = "奖项配置 json")
    private String awardConfig;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time" , fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
