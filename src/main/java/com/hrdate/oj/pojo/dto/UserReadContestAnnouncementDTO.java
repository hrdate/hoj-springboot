package com.hrdate.oj.pojo.dto;

import lombok.Data;

import java.util.List;


/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */

@Data
public class UserReadContestAnnouncementDTO {

    private Long cid;

    private List<Long> readAnnouncementList;
}