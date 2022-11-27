package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrdate.oj.entity.judge.Judge;
import com.hrdate.oj.pojo.vo.ProblemCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Mapper
public interface JudgeMapper extends BaseMapper<Judge> {
    List<ProblemCountVO> getProblemListCount(@Param("pidList") List<Long> pidList);

    ProblemCountVO getProblemCount(@Param("pid") Long pid, @Param("gid") Long gid);

}
