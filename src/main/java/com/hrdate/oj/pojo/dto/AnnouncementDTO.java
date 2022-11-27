package com.hrdate.oj.pojo.dto;

import com.hrdate.oj.entity.common.Announcement;
import lombok.Data;


import javax.validation.constraints.NotBlank;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
public class AnnouncementDTO {
    @NotBlank(message = "比赛id不能为空")
    private Long cid;

    private Announcement announcement;
}