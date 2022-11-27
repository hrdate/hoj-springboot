package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrdate.oj.entity.problem.Problem;
import com.hrdate.oj.pojo.vo.ProblemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 题目
 * @author: huangrendi
 * @date: 2022-11-26
 **/

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {

    List<ProblemVO> getProblemList(IPage page,
                                   @Param("pid") Long pid,
                                   @Param("keyword") String keyword,
                                   @Param("difficulty") Integer difficulty,
                                   @Param("tid") List<Long> tid,
                                   @Param("tagListSize") Integer tagListSize,
                                   @Param("oj") String oj);
}
