package com.hrdate.oj.hander;

import com.alibaba.fastjson.JSON;
import com.hrdate.oj.response.CommonResponse;
import com.hrdate.oj.dto.user.UserDetailDTO;
import com.hrdate.oj.entity.UserAuthModel;
import com.hrdate.oj.utils.JacksonUtil;
import com.hrdate.oj.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.hrdate.oj.enums.ReturnCode.FORBIDDEN;
import static com.hrdate.oj.enums.ReturnCode.UNAUTHORIZED;
import static com.hrdate.oj.constant.CommonConst.APPLICATION_JSON;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-09
 **/

@Slf4j
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public static ThreadLocal<String> currentUsername = new ThreadLocal<>();

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        try {
            UserAuthModel userAuthModel = JacksonUtil.readValue(httpServletRequest.getInputStream(), UserAuthModel.class);
            currentUsername.set(userAuthModel.getUsername());

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userAuthModel.getUsername(), userAuthModel.getPassword()
                    )
            );
        } catch (Exception exception) {
            httpServletResponse.setContentType(APPLICATION_JSON);
            httpServletResponse.getWriter().write(JSON.toJSONString(CommonResponse.failedResult("非法请求" , FORBIDDEN.getCode())));
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        // 生成jwt
        String jwt = JwtTokenUtil.generateToken(authResult.getName(), authResult.getAuthorities());

        // 把jwt放入header
        response.setContentType(APPLICATION_JSON);
        response.setHeader("Authorization", jwt);
        UserDetailDTO userDetailDTO = (UserDetailDTO) authResult.getPrincipal();
        userDetailDTO.setPassword(null);
        Map<String, Object> map = new HashMap<>();
        map.put("userDetail", userDetailDTO);
        map.put("token", jwt);
        response.getWriter().write(JSON.toJSONString(CommonResponse.succeedResult(map)));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException exception) throws IOException {
        //登录不成功时，会抛出对应的异常
        String message = exception.getMessage();
        if (exception instanceof LockedException) {
            message = "账号被锁定";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "密码过期";
        } else if (exception instanceof AccountExpiredException) {
            message = "账号过期";
        } else if (exception instanceof DisabledException) {
            message = "账号被禁用";
        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误";
        }
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(CommonResponse.failedResult(message , UNAUTHORIZED.getCode())));

    }


}
