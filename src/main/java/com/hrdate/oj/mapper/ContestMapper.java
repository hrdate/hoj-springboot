package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrdate.oj.entity.contest.Contest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Mapper
public interface ContestMapper extends BaseMapper<Contest> {
}
