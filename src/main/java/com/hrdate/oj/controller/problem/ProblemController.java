package com.hrdate.oj.controller.problem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrdate.oj.annotation.AnonApi;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.dto.LastAcceptedCodeVO;
import com.hrdate.oj.pojo.dto.PidListDTO;
import com.hrdate.oj.pojo.vo.ProblemFullScreenListVO;
import com.hrdate.oj.pojo.vo.ProblemInfoVO;
import com.hrdate.oj.pojo.vo.ProblemVO;
import com.hrdate.oj.pojo.vo.RandomProblemVO;
import com.hrdate.oj.service.problem.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * @Author: Himit_ZH
 * @Date: 2020/10/27 13:24
 * @Description: 问题数据控制类，处理题目列表请求，题目内容请求。
 */
@Api(tags = "题目模块")
@RestController("/api")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @ApiOperation(value = "获取题目列表分页")
    @GetMapping(value = "/get-problem-list")
    public CommonResult<Page<ProblemVO>> getProblemList(@RequestParam(value = "limit", required = false) Integer limit,
                                                        @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                                        @RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "tagId", required = false) List<Long> tagId,
                                                        @RequestParam(value = "difficulty", required = false) Integer difficulty,
                                                        @RequestParam(value = "oj", required = false) String oj) {

        return problemService.getProblemList(limit, currentPage, keyword, tagId, difficulty, oj);
    }

    @ApiOperation(value = "随机选取一道题目")
    @GetMapping("/get-random-problem")
    public CommonResult<RandomProblemVO> getRandomProblem() {
        return problemService.getRandomProblem();
    }

    @ApiOperation(value = "获取用户对应该题目列表中各个题目的做题情况")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @PostMapping("/get-user-problem-status")
    public CommonResult<HashMap<Long, Object>> getUserProblemStatus(@Validated @RequestBody PidListDTO pidListDto) {
        return problemService.getUserProblemStatus(pidListDto);
    }


    @ApiOperation(value = "获取指定题目的详情信息，标签，所支持语言，做题情况（只能查询公开题目 也就是auth为1）")
    @GetMapping(value = "/get-problem-detail")
    @AnonApi
    public CommonResult<ProblemInfoVO> getProblemInfo(@RequestParam(value = "problemId", required = true) String problemId,
                                                      @RequestParam(value = "gid", required = false) Long gid) {
        return problemService.getProblemInfo(problemId, gid);
    }

    @ApiOperation(value = "获取用户对于该题最近AC的代码")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @GetMapping("/get-last-ac-code")
    public CommonResult<LastAcceptedCodeVO> getUserLastAcceptedCode(@RequestParam(value = "pid") Long pid,
                                                                    @RequestParam(value = "cid", required = false) Long cid) {
        return problemService.getUserLastAcceptedCode(pid, cid);
    }

    @ApiOperation(value = "获取专注模式页面底部的题目列表")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @GetMapping("/get-full-screen-problem-list")
    public CommonResult<List<ProblemFullScreenListVO>> getFullScreenProblemList(
            @RequestParam(value = "cid", required = true) Long cid) {
        return problemService.getFullScreenProblemList(cid);
    }

}