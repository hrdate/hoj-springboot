package top.hcode.hoj.pojo.vo;

import lombok.Data;
import top.hcode.hoj.pojo.entity.judge.JudgeCase;

import java.util.List;

/**
 * @Description:
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
