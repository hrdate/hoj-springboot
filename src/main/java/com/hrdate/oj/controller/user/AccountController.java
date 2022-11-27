package com.hrdate.oj.controller.user;

import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.dto.CheckUsernameOrEmailDTO;
import com.hrdate.oj.pojo.dto.UserInfoChangeDTO;
import com.hrdate.oj.pojo.vo.CheckUsernameOrEmailVO;
import com.hrdate.oj.pojo.vo.UserHomeVO;
import com.hrdate.oj.pojo.vo.UserInfoVO;
import com.hrdate.oj.service.user.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-23
 **/

@Api(tags = "用户账号模块")
@RestController("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "检验用户名和邮箱是否存在")
    @PostMapping(value = "/check-username-or-email")
    public CommonResult<CheckUsernameOrEmailVO> checkUsernameOrEmail(@RequestBody CheckUsernameOrEmailDTO checkUsernameOrEmailDto) {
        return accountService.checkUsernameOrEmail(checkUsernameOrEmailDto);
    }


    @ApiOperation(value = "前端userHome用户个人主页的数据请求，主要是返回解决题目数，AC的题目列表，提交总数，AC总数")
    @GetMapping("/get-user-home-info")
    public CommonResult<UserHomeVO> getUserHomeInfo(@RequestParam(value = "uid", required = false) String uid,
                                                    @RequestParam(value = "username", required = false) String username) {
        return accountService.getUserHomeInfo(uid, username);
    }


    @ApiOperation(value = "用户修改自己的基本信息")
    @PostMapping("/change-userInfo")
    @PreAuthorize("hasAnyRole('user')")
    public CommonResult<?> changeUserInfo(@RequestBody UserInfoChangeDTO userInfoChangeDTO) {
        return accountService.changeUserInfo(userInfoChangeDTO);
    }
}
