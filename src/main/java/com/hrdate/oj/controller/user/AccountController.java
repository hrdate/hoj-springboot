package com.hrdate.oj.controller.user;

import com.hrdate.oj.annotation.AnonApi;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.dto.*;
import com.hrdate.oj.pojo.vo.CheckUsernameOrEmailVO;
import com.hrdate.oj.pojo.vo.RegisterCodeVO;
import com.hrdate.oj.pojo.vo.UserHomeVO;
import com.hrdate.oj.pojo.vo.UserInfoVO;
import com.hrdate.oj.service.user.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

    @ApiOperation("调用邮件服务，发送注册流程的6位随机验证码")
    @GetMapping(value = "/get-register-code")
    @AnonApi
    public CommonResult<RegisterCodeVO> getRegisterCode(@RequestParam(value = "email", required = true) String email) {
        return accountService.getRegisterCode(email);
    }

    @ApiOperation("注册逻辑，具体参数请看RegisterDto类")
    @PostMapping("/register")
    @AnonApi
    public CommonResult<?> register(@Validated @RequestBody RegisterDTO registerDto) {
        return accountService.register(registerDto);
    }


    @ApiOperation("发送重置密码的链接邮件")
    @PostMapping("/apply-reset-password")
    @AnonApi
    public CommonResult<?> applyResetPassword(@Validated @RequestBody ApplyResetPasswordDTO applyResetPasswordDto) {
        return accountService.applyResetPassword(applyResetPasswordDto);
    }


    /**
     * @param resetPasswordDto
     * @MethodName resetPassword
     * @Description
     * @Return
     * @Since 2020/11/6
     */
    @ApiOperation("用户重置密码")
    @PostMapping("/reset-password")
    @AnonApi
    public CommonResult<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDto) {
        return accountService.resetPassword(resetPasswordDto);
    }
}
