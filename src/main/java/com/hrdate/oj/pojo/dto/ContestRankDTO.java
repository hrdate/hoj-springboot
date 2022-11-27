package com.hrdate.oj.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
public class ContestRankDTO {
    /***
     *     @param cid           比赛id
     *     @param removeStar    是否移除打星队伍
     *     @param forceRefresh  是否强制实时榜单
     *     @param concernedList 关注的用户(uuid)列表
     *     @param keyword       搜索关键词：学校或榜单显示名称
     */
    private Long cid;

    private Integer limit;

    private Integer currentPage;

    private Boolean forceRefresh;

    private Boolean removeStar;

    private String keyword;

    private List<String> concernedList;

    private List<Integer> externalCidList;
}