package com.hrdate.oj.hander;

import com.alibaba.fastjson.JSON;
import com.hrdate.oj.annotation.AnonApi;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.utils.JwtTokenUtil;
import com.hrdate.oj.utils.SpringContextUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

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
 * @description: JwtFilter 通用拦截器
 * @author: huangrendi
 * @date: 2022/11/18  0:14
 */
@Slf4j
public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 若有AnonApi 开放接口，无需登录认证
        WebApplicationContext ctx = RequestContextUtils.findWebApplicationContext(request);
        RequestMappingHandlerMapping mapping = ctx.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        try {
            HandlerExecutionChain handler = mapping.getHandler(request);
            HandlerMethod handlerClazz = (HandlerMethod) handler.getHandler();
            // 判断请求是否访问的是公共接口，如果拥有@AnonApi注解则不再走登录认证，直接访问controller对应的方法
            AnonApi anonApi = SpringContextUtil.getAnnotation(handlerClazz.getMethod(),
                    handlerClazz.getBeanType(),
                    AnonApi.class);
            if(anonApi != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (Exception e) {
            log.error("AnonApi开放接口，无需登录认证异常", e.getMessage());
        }
        // 非开放接口，校验用户jwt
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
                response.getWriter().write(JSON.toJSONString(CommonResult.failedResult("凭证已失效，请重新登录！" , FORBIDDEN.getCode())));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}