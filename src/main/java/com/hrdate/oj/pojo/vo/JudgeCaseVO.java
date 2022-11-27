package com.hrdate.oj.pojo.vo;

import com.hrdate.oj.entity.judge.JudgeCase;
import lombok.Data;


import java.util.List;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 */
@Data
public class JudgeCaseVO {

    /**
     * 当judgeCaseMode为default时
     */
    private List<JudgeCase> judgeCaseList;

    /**
     * 当judgeCaseMode为subtask_lowest,subtask_average时
     */
    private List<SubTaskJudgeCaseVO> subTaskJudgeCaseVoList;

    /**
     * default,subtask_lowest,subtask_average
     */
    private String judgeCaseMode;
}
