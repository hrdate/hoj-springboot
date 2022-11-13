package com.hrdate.oj.hander;

import com.alibaba.fastjson.JSON;
import com.hrdate.oj.annotation.AccessLimit;
import com.hrdate.oj.response.CommonResponse;
import com.hrdate.oj.utils.IpUtil;
import com.hrdate.oj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.hrdate.oj.constant.CommonConst.APPLICATION_JSON;

/**
 * @description: redis计数器限流
 * @author: huangrendi
 * @date: 2022-11-11
 **/
@Slf4j
public class WebSecurityHandler implements HandlerInterceptor {

    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 如果请求输入方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            // 获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                long seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                // 关于key的生成规则可以自己定义 本项目需求是对每个方法都加上限流功能，如果你只是针对ip地址限流，那么key只需要只用ip就好
                String key = IpUtil.getIpAddress(httpServletRequest) + hm.getMethod().getName();
                // 从redis中获取用户访问的次数
                try {
                    // 此操作代表获取该key对应的值自增1后的结果
                    long q = redisUtil.incrExpire(key, seconds);
                    if (q > maxCount) {
                        render(httpServletResponse, CommonResponse.failedResult("请求过于频繁，请稍候再试"));
                        log.warn(key + "请求次数超过每" + seconds + "秒" + maxCount + "次");
                        return false;
                    }
                    return true;
                } catch (RedisConnectionFailureException e) {
                    log.warn("redis错误: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CommonResponse<?> result) throws Exception {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
