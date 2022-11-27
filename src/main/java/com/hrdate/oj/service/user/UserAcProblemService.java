package com.hrdate.oj.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.user.UserAcProblem;
import com.hrdate.oj.mapper.UserAcProblemMapper;
import com.hrdate.oj.pojo.vo.ACMRankVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 用户ac题目
 * @author: huangrendi
 * @date: 2022-11-26
 **/

@Slf4j(topic = "用户ac题目")
@Service
public class UserAcProblemService extends ServiceImpl<UserAcProblemMapper, UserAcProblem> {
    @Autowired
    private UserAcProblemMapper userAcProblemMapper;
    public IPage<ACMRankVO> getACMRankList(Page<ACMRankVO> page, List<String> uidList) {
        return userAcProblemMapper.getACMRankList(page, uidList);
    }
}
