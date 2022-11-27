package com.hrdate.oj.controller.contest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.service.contest.RankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Api(tags = "排名")
@RestController("/api")
public class RankController {

    @Autowired
    private RankService rankService;


    @ApiOperation(value = "获取排行榜数据")
    @GetMapping("/get-rank-list")
    public CommonResult<IPage> getRankList(@RequestParam(value = "limit", required = false) Integer limit,
                                           @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                           @RequestParam(value = "searchUser", required = false) String searchUser,
                                           @RequestParam(value = "type", required = true) Integer type) {
        return rankService.getRankList(limit, currentPage, searchUser, type);
    }
}
