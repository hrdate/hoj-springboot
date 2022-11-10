package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrdate.oj.entity.UserInfoModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 用户信息
 * @author: huangrendi
 * @date: 2022-11-09
 **/

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoModel> {
}
