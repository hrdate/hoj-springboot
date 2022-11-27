package com.hrdate.oj.utils;


import com.hrdate.oj.pojo.dto.UserDetailDTO;
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

    public static Boolean checkCurrentUserRole(String role) {
        UserDetailDTO loginUser = getLoginUser();
        return loginUser.getRoleList().contains(role.trim());
    }
}
