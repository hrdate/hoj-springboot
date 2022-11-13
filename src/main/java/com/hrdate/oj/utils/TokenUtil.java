package com.hrdate.oj.utils;


import com.hrdate.oj.dto.user.UserDetailDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    private TokenUtil() {
    }

    /**
     * 获取当前登录用户
     *
     * @return 用户登录信息
     */
    public static UserDetailDTO getLoginUser() {
        return (UserDetailDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public static String getCurrentUsername() {
        return getLoginUser().getUsername();
    }

    public static String getCurrentNickname() {
        return getLoginUser().getNickname();
    }
}
