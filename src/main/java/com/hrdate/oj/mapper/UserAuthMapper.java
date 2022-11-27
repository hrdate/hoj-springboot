package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrdate.oj.entity.user.UserAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 用户
 * @author: huangrendi
 * @date: 2022-11-08
 **/

@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {
}
