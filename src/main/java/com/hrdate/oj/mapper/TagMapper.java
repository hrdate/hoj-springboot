package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrdate.oj.entity.problem.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
