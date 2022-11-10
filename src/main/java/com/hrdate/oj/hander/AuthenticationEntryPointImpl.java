package com.hrdate.oj.hander;

import com.alibaba.fastjson.JSON;
import com.hrdate.oj.response.CommonResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hrdate.oj.constant.CommonConst.APPLICATION_JSON;
import static com.hrdate.oj.enums.ReturnCode.UNAUTHORIZED;


/**
 * 用户未登录处理
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter()
                .write(
                        JSON.toJSONString(
                                CommonResponse.failedResult(UNAUTHORIZED.getMsg(), UNAUTHORIZED.getCode())
                        )
                );
    }

}
