package com.hrdate.oj.controller.common;

import com.hrdate.oj.annotation.AnonApi;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.vo.CaptchaVO;
import com.hrdate.oj.service.common.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Api(tags = "通用模块")
@RestController("/api")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    @AnonApi
    public CommonResult<CaptchaVO> getCaptcha() {
        return commonService.getCaptcha();
    }
}
