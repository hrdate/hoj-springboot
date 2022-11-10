package com.hrdate.oj.hander;

import com.alibaba.fastjson.JSON;
import com.hrdate.oj.dto.user.UserDetailDTO;
import com.hrdate.oj.entity.UserAuthModel;
import com.hrdate.oj.response.CommonResponse;
import com.hrdate.oj.service.UserAuthService;
import com.hrdate.oj.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.hrdate.oj.constant.CommonConst.APPLICATION_JSON;


/**
 * 登录成功处理
 *
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private UserAuthService userAuthService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // 返回登录信息
        UserDetailDTO userDetailDTO = TokenUtil.getLoginUser();
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(CommonResponse.succeedResult(userDetailDTO)));
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }

    /**
     * 更新用户信息，最近登陆的时间，ip
     */
    @Async
    public void updateUserInfo() {
        UserDetailDTO userDetailDTO = (UserDetailDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAuthModel userAuth = userAuthService.getById(userDetailDTO.getId());
        userAuth.setIpAddress(userDetailDTO.getIpAddress());
        userAuth.setIpSource(userDetailDTO.getIpSource());
        userAuth.setLastLoginTime(userDetailDTO.getLastLoginTime());
        userAuthService.updateById(userAuth);
    }

}
