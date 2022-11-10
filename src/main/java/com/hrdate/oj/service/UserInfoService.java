package com.hrdate.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.UserInfoModel;
import com.hrdate.oj.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @description: 用户信息
 * @author: huangrendi
 * @date: 2022-11-09
 **/

@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfoModel> {

}
