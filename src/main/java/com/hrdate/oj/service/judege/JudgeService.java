package com.hrdate.oj.service.judege;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.judge.Judge;
import com.hrdate.oj.mapper.JudgeMapper;
import com.hrdate.oj.pojo.vo.ProblemCountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Service
public class JudgeService extends ServiceImpl<JudgeMapper, Judge> {
    @Autowired
    private JudgeMapper judgeMapper;

    /**
     * 根据题目pid查询 题目提交情况
     * @param pidList
     * @return
     */
    public List<ProblemCountVO> getProblemListCount(List<Long> pidList) {
        return judgeMapper.getProblemListCount(pidList);

    }

    public ProblemCountVO getProblemCount(Long pid, Long gid) {
        return judgeMapper.getProblemCount(pid, gid);
    }
}
