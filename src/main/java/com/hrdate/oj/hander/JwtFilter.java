package com.hrdate.oj.hander;

import com.alibaba.fastjson.JSON;
import com.hrdate.oj.response.CommonResponse;
import com.hrdate.oj.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.hrdate.oj.enums.ReturnCode.FORBIDDEN;
import static com.hrdate.oj.constant.CommonConst.APPLICATION_JSON;

/**
 * @ClassName: JwtFilter
 * @Description: TODO
 * @author: datealive
 * @date: 2021/4/26  0:14
 */
@Slf4j
public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //后台管理路径外的请求直接跳过
        if (request.getRequestURI().startsWith("/admin") || request.getRequestURI().startsWith("/api")) {
            String jwt = request.getHeader("Authorization");
            if (JwtTokenUtil.judgeTokenIsExist(jwt)) {
                try {
                    Claims claims = JwtTokenUtil.getTokenBody(jwt);
                    String username = claims.getSubject();
                    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(token);
                } catch (Exception e) {
                    log.error("JwtFilter拦截器异常", e.getMessage());
                    response.setContentType(APPLICATION_JSON);
                    response.getWriter().write(JSON.toJSONString(CommonResponse.failedResult("凭证已失效，请重新登录！" , FORBIDDEN.getCode())));
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }
}