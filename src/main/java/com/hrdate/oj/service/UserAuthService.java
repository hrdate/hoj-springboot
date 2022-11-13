package com.hrdate.oj.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.dto.user.UserDetailDTO;
import com.hrdate.oj.entity.UserAuthModel;
import com.hrdate.oj.entity.UserInfoModel;
import com.hrdate.oj.mapper.UserAuthMapper;
import com.hrdate.oj.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @description: 用户
 * @author: huangrendi
 * @date: 2022-11-08
 **/

@Slf4j
@Service
public class UserAuthService extends ServiceImpl<UserAuthMapper, UserAuthModel> implements UserDetailsService {
    @Resource
    private HttpServletRequest request;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用户登录信息
        String loginType = "用户名";
        // 根据用户名username查询
        LambdaQueryWrapper<UserAuthModel> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(StringUtils.isNoneBlank(username), UserAuthModel::getUsername, username);
        UserAuthModel userAuthModel = this.getOne(queryWrapper,false);
        // 当以用户名username找不到时，则根据邮箱email查询
        if(userAuthModel == null){
            userAuthModel = this.getOne(new LambdaQueryWrapper<UserAuthModel>()
                    .eq(StringUtils.isNoneBlank(username), UserAuthModel::getEmail, username), false);
            loginType = "邮箱";
        }
        // 用户不存在，异常
        if(userAuthModel == null){
            log.info("用户名[" + username + "]不存在，请校验参数合法性！");
            throw new UsernameNotFoundException("用户名[" + username + "]不存在，请校验参数合法性！");
        }
        // 封装用户登陆详细信息
        UserDetailDTO userDetailDTO = convertUserDetail(userAuthModel, request);
        userDetailDTO.setLoginType(loginType);
        log.info("封装用户登陆详细信息 userDetailDTO:{}", userDetailDTO);
        // 用户{}更新ip，最近登录时间
        this.updateUserInfo(userDetailDTO);
        return userDetailDTO;
    }

    /**
     * 封装用户登录信息
     *
     * @param userAuthModel    用户账号
     * @param request 请求
     * @return 用户登录信息
     */
    public UserDetailDTO convertUserDetail(UserAuthModel userAuthModel, HttpServletRequest request) {
        // 获取用户ip信息
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);

        // 用户信息详情
        UserInfoModel userInfoModel = userInfoService.getById(userAuthModel.getUserInfoId());

        // 构建用户信息
        return UserDetailDTO.builder()
                .id(userAuthModel.getId())
                .userInfoId(userInfoModel.getId())
                .username(userAuthModel.getUsername())
                .email(userAuthModel.getEmail())
                .password(userAuthModel.getPassword())
                .roleList(userAuthModel.getRoleList())
                .nickname(userInfoModel.getNickname())
                .intro(userInfoModel.getIntro())
                .avatar(userInfoModel.getAvatar())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .lastLoginTime(LocalDateTime.now())
                .isDisable(userAuthModel.getIsDisable())
                .build();
    }

    /**
     * 更新用户信息，最近登陆的时间，ip
     */
    @Async
    public void updateUserInfo(UserDetailDTO userDetailDTO) {
        UserAuthModel userAuth = this.getById(userDetailDTO.getId());
        userAuth.setIpAddress(userDetailDTO.getIpAddress());
        userAuth.setIpSource(userDetailDTO.getIpSource());
        userAuth.setLastLoginTime(userDetailDTO.getLastLoginTime());
        this.updateById(userAuth);
    }
}
