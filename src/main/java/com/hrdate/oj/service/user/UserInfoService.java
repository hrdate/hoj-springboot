package com.hrdate.oj.service.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.user.UserInfo;
import com.hrdate.oj.mapper.UserInfoMapper;
import com.hrdate.oj.pojo.dto.RegisterDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 用户信息
 * @author: huangrendi
 * @date: 2022-11-09
 **/

@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo> {

    /**
     * 添加一个普通用户
     * @param registerDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(RegisterDTO registerDto) {
        UserInfo userInfo = UserInfo.builder()
                .uid(registerDto.getUid())
                .username(registerDto.getUsername())
                .build();
        return this.save(userInfo);
    }
}
