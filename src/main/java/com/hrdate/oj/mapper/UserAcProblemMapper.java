package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrdate.oj.entity.user.UserAcProblem;
import com.hrdate.oj.pojo.vo.ACMRankVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 用户ac题目
 * @author: huangrendi
 * @date: 2022-11-26
 **/

@Mapper
public interface UserAcProblemMapper extends BaseMapper<UserAcProblem> {
    IPage<ACMRankVO> getACMRankList(Page<ACMRankVO> page, @Param("uidList") List<String> uidList);
}
