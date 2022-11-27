package com.hrdate.oj.hander;

import com.alibaba.fastjson.JSON;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.dto.UserDetailDTO;
import com.hrdate.oj.entity.user.UserAuth;
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

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        // 注入AuthenticationManager来处理子类中定义的 authenticationToken
        setAuthenticationManager(authenticationManager);
    }

    /**
     * 尝试认证处理
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        try {
            UserAuth userAuth = JacksonUtil.readValue(httpServletRequest.getInputStream(), UserAuth.class);

            log.info("尝试认证处理用户名:{}，密码:{}", userAuth.getUsername(), userAuth.getPassword());
            /**
             * 子类通过实现 attemptAuthentication 来尝试认证处理
             * 认证成功后，将会将成功认证的信息 Authentication 保存到线程本地 SecurityContext中
             */
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userAuth.getUsername(), userAuth.getPassword()
                    )
            );
        } catch (Exception exception) {
            log.error("尝试认证处理用户失败", exception);
            httpServletResponse.setContentType(APPLICATION_JSON);
            httpServletResponse.getWriter().write(JSON.toJSONString(CommonResult.failedResult("非法请求", FORBIDDEN.getCode(), exception.getMessage() , null)));
        }
        return null;
    }

    /**
     * 认证成功后处理
     * @param request
     * @param response
     * @param chain
     * @param authentication
     * @throws IOException
     */
//    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException {
        // 生成jwt
        String jwt = JwtTokenUtil.generateToken(authentication.getName(), authentication.getAuthorities());
        // 把jwt放入header
        response.setContentType(APPLICATION_JSON);
        response.setHeader("Authorization", jwt);
        UserDetailDTO userDetailDTO = (UserDetailDTO) authentication.getPrincipal();
        userDetailDTO.setPassword(null);
        Map<String, Object> map = new HashMap<>();
        map.put("userDetail", userDetailDTO);
        map.put("token", jwt);
        response.getWriter().write(JSON.toJSONString(CommonResult.succeedResult(map)));

    }

    /**
     * 认证失败后处理
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException exception) throws IOException {
        //登录不成功时，会抛出对应的异常
        String message = exception.getMessage();
        if (exception instanceof LockedException) {
            message = "用户账号被锁定:" + message;
        } else if (exception instanceof CredentialsExpiredException) {
            message = "密码过期:" + message;
        } else if (exception instanceof AccountExpiredException) {
            message = "用户账号过期:" + message;
        } else if (exception instanceof DisabledException) {
            message = "用户账号被禁用:" + message;
        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误:" + message;
        }
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(CommonResult.failedResult(message , UNAUTHORIZED.getCode())));

    }


}
